package com.stocksexchange.android.ui.deposit.fragment

import android.Manifest
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ProgressBar
import com.google.zxing.EncodeHintType
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.model.Wallet
import com.stocksexchange.android.ui.base.fragments.BaseDataLoadingFragment
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.BorderedMapView
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.ui.views.dialogs.QrDialog
import com.stocksexchange.android.utils.extensions.truncate
import com.stocksexchange.android.utils.formatters.DoubleFormatter
import com.stocksexchange.android.utils.helpers.*
import com.stocksexchange.android.utils.providers.InfoViewProvider
import kotlinx.android.synthetic.main.deposit_fragment_layout.view.*
import net.glxn.qrgen.android.QRCode
import net.glxn.qrgen.core.image.ImageType
import java.io.File

class DepositFragment : BaseDataLoadingFragment<DepositPresenter, Deposit>(), DepositContract.View {


    companion object {

        private const val SAVED_STATE_WALLET = "wallet"
        private const val SAVED_STATE_DEPOSIT = "deposit"


        fun newInstance(wallet: Wallet): DepositFragment {
            val fragment = DepositFragment()

            fragment.mWallet = wallet

            return fragment
        }

    }


    private lateinit var mWallet: Wallet

    private var mDeposit: Deposit? = null

    private var mQrDialog: QrDialog? = null




    override fun initPresenter(): DepositPresenter = DepositPresenter(this)


    override fun init() {
        super.init()

        initCurrencyTextView()
        initMainContainer()
        initDetails()
    }


    private fun initCurrencyTextView() {
        mRootView.currencyTv.text = mWallet.currency.name

        ThemingUtil.Deposit.currency(mRootView.currencyTv, getAppTheme())
    }


    private fun initMainContainer() {
        initButtonsContainer()
        initHint()

        if(!isDataSourceEmpty()) {
            addData(mDeposit!!)
        }
    }


    private fun initButtonsContainer() {
        with(ThemingUtil.Deposit) {
            val theme = getAppTheme()

            borderedMap(mRootView.firstInfoContainerBmv, theme)
            borderedMap(mRootView.secondInfoContainerBmv, theme)
            borderedMap(mRootView.thirdInfoContainerBmv, theme)
        }
    }


    private fun initHint() {
        ThemingUtil.Deposit.hint(mRootView.hintTv, getAppTheme())
    }


    private fun initDetails() {
        with(mRootView) {
            val formatter = DoubleFormatter.getInstance(ctx.getLocale())

            val depositFeeArg = "${formatter.formatTransactionFee(mWallet.currency.depositFee)} ${mWallet.currency.depositFeeCurrency}"
            depositFeeTv.text = getString(R.string.deposit_fragment_deposit_fee_template, depositFeeArg)

            val minDepositAmountArg = "${formatter.formatAmount(mWallet.currency.minimumDepositAmount)} ${mWallet.currency.name}"
            minDepositAmountTv.text = getString(R.string.deposit_fragment_min_amount_template, minDepositAmountArg)

            warningTv.text = getString(R.string.deposit_fragment_warning_template, mWallet.currency.name)
        }
    }


    override fun adjustView(view: View) {
        when(view.id) {

            R.id.infoView -> {
                mRootView.infoView.setTopPadding(0)
                mRootView.infoView.setBottomPadding(dimenInPx(R.dimen.deposit_fragment_info_view_vertical_padding))
            }

        }
    }


    override fun addData(data: Deposit) {
        mDeposit = data

        val defaultInfoContainerHeight = resources.getDimensionPixelSize(R.dimen.deposit_fragment_bmv_height)
        val infoContainerHeight = (when {
            data.hasAtLeastThreeItems() -> defaultInfoContainerHeight
            data.hasAtLeastTwoItems() -> defaultInfoContainerHeight + (defaultInfoContainerHeight / 2)
            data.hasAtLeastOneItem() -> defaultInfoContainerHeight * 3

            else -> throw IllegalStateException("The returned deposit data is empty.")
        })
        val isSubtitleMultilineTextEnabled = (
            ((ctx.getSmallestScreenWidthInDp() >= 720) && data.hasAtLeastThreeItems()) ||
            ((ctx.getSmallestScreenWidthInDp() >= 600) && !data.hasAtLeastThreeItems() && data.hasAtLeastTwoItems()) ||
            (!data.hasAtLeastThreeItems() && !data.hasAtLeastTwoItems() && data.hasAtLeastOneItem())
        )

        if(data.hasAddress()) {
            fillOutInfoContainer(
                mRootView.firstInfoContainerBmv,
                infoContainerHeight,
                getString(R.string.deposit_address),
                data.address,
                isSubtitleMultilineTextEnabled,
                { mPresenter?.onDepositAddressClicked(data.address) },
                { mPresenter?.onDepositAddressLongClicked(data.address) }
            )
        } else {
            mRootView.firstInfoContainerBmv.makeGone()
        }

        if(data.hasPublicKey()) {
            fillOutInfoContainer(
                mRootView.secondInfoContainerBmv,
                infoContainerHeight,
                getString(R.string.public_key),
                data.publicKey,
                isSubtitleMultilineTextEnabled,
                { mPresenter?.onPublicKeyClicked(data.publicKey) },
                { mPresenter?.onPublicKeyLongClicked(data.publicKey) }
            )
        } else {
            mRootView.secondInfoContainerBmv.makeGone()
        }

        if(data.hasPaymentId()) {
            fillOutInfoContainer(
                mRootView.thirdInfoContainerBmv,
                infoContainerHeight,
                getString(R.string.payment_id),
                data.paymentId,
                isSubtitleMultilineTextEnabled,
                { mPresenter?.onPaymentIdClicked(data.paymentId) },
                { mPresenter?.onPaymentIdLongClicked(data.paymentId) }
            )
        } else if(data.hasDestinationTag()) {
            fillOutInfoContainer(
                mRootView.thirdInfoContainerBmv,
                infoContainerHeight,
                getString(R.string.destination_tag),
                data.destinationTag,
                isSubtitleMultilineTextEnabled,
                { mPresenter?.onDestinationTagClicked(data.destinationTag) },
                { mPresenter?.onDestinationTagLongClicked(data.destinationTag) }
            )
        } else {
            mRootView.thirdInfoContainerBmv.makeGone()
        }
    }


    private fun fillOutInfoContainer(bmv: BorderedMapView, height: Int,
                                     title: String, subtitle: String,
                                     isSubtitleMultilineTextEnabled: Boolean, onClickListener: () -> Unit,
                                     onLongClickListener: () -> Unit) {
        with(bmv) {
            makeVisible()

            val layoutParameters = layoutParams
            layoutParameters.height = height
            layoutParams = layoutParameters

            setTitleText(title)
            setSubtitleText(if(isSubtitleMultilineTextEnabled) subtitle else subtitle.truncate(getDepositHashLength()))
            setSubtitleMultilineTextEnabled(isSubtitleMultilineTextEnabled)
            setOnClickListener {
                onClickListener()
            }
            setOnLongClickListener {
                onLongClickListener()
                true
            }
        }
    }


    override fun showQrDialog(hash: String, title: String) {
        val dialog = QrDialog(ctx)
        val qrCodeSize = dimenInPx(R.dimen.qr_dialog_qr_code_iv_size)

        dialog.setTitleText(title)
        dialog.setHash(hash)
        dialog.setQrImage(QRCode.from(hash).withSize(qrCodeSize, qrCodeSize).withHint(EncodeHintType.MARGIN, 0).bitmap())
        dialog.setCopyHashButtonClickListener {
            mPresenter?.onCopyHashButtonClicked(hash)
        }
        dialog.setSaveImageButtonClickListener {
            onSaveQrImageButtonClicked()
        }

        ThemingUtil.Dialogs.qrDialog(dialog, getAppTheme())

        mQrDialog = dialog
        mQrDialog!!.show()
    }


    override fun dismissQrDialog() {
        mQrDialog?.dismiss()
    }


    override fun downloadQrCodeImage() {
        val title = mQrDialog!!.getTitleText().replace(" ", "_").toLowerCase()
        val hash = mQrDialog!!.getHash()

        val outputFile = File(
            createParentDirectory("${Environment.DIRECTORY_PICTURES}/${getString(R.string.app_name)}"),
            composeFileName(
                FILE_NAME_PREFIX_IMAGE,
                "${title}_qr_code",
                System.currentTimeMillis().toString(),
                FILE_NAME_EXTENSION_JPG
            )
        )

        QRCode.from(hash)
            .withSize(512, 512)
            .withHint(EncodeHintType.MARGIN, 0)
            .to(ImageType.JPG)
            .writeTo(outputFile.outputStream())

        super.showToast(getString(R.string.qr_code_image_downloaded))
    }


    private fun getDepositHashLength(): Int {
        val screenWidthInDp = ctx.getSmallestScreenWidthInDp()

        return when {
            (screenWidthInDp < 600) -> 25
            (screenWidthInDp < 720) -> 45

            else -> 50
        }
    }


    override fun getCurrency(): String {
        return mWallet.currency.name
    }


    override fun isDataSourceEmpty(): Boolean {
        return (mDeposit == null)
    }


    override fun isDataCacheable(): Boolean = false


    override fun getInfoViewIcon(infoViewProvider: InfoViewProvider): Drawable? {
        return infoViewProvider.getDepositIcon()
    }


    override fun getEmptyViewCaption(infoViewProvider: InfoViewProvider): String {
        return infoViewProvider.getDepositEmptyCaption()
    }


    override fun getMainView(): View = mRootView.buttonsContainerLl


    override fun getInfoView(): InfoView = mRootView.infoView


    override fun getProgressBar(): ProgressBar = mRootView.progressBar


    override fun getRefreshProgressBar(): SwipeRefreshLayout = mRootView.swipeRefreshLayout


    override fun getContentLayoutResourceId(): Int = R.layout.deposit_fragment_layout


    override fun getDeposit(): Deposit? {
        return mDeposit
    }


    private fun onSaveQrImageButtonClicked() {
        if(!checkPermissions(Constants.REQUEST_CODE_STORAGE_PERMISSION, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            return
        }

        mPresenter?.onSaveQrCodeImageButtonClicked()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
                                            grantResults: IntArray) {
        if(isPermissionSetGranted(grantResults)) {
            if(requestCode == Constants.REQUEST_CODE_STORAGE_PERMISSION) {
                onSaveQrImageButtonClicked()
            }
        } else {
            showToast(getString(R.string.error_permissions_not_granted))
        }
    }


    override fun onRestoreState(savedState: Bundle?) {
        if(savedState != null) {
            with(savedState) {
                mWallet = getParcelable(SAVED_STATE_WALLET)!!
                mDeposit = getParcelable(SAVED_STATE_DEPOSIT)
            }
        }

        super.onRestoreState(savedState)
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        with(savedState) {
            putParcelable(SAVED_STATE_WALLET, mWallet)
            putParcelable(SAVED_STATE_DEPOSIT, mDeposit)
        }
    }


}