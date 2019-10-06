package com.nike.adapt.util.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.WorkerThread
import androidx.core.content.edit
import com.google.gson.Gson
import com.nike.adapt.BigfootApplication
import com.nike.adapt.AdaptExecutor
import com.nike.adapt.network.listener.AdaptResultListener
import com.nike.adapt.network.listener.onFailure
import com.nike.adapt.network.listener.onSuccess
import com.nike.innovation.android.bigfoot.ble.model.color.BigfootColorPreset
import com.nike.innovation.android.bigfoot.ble.model.device.BigfootDeviceColor
import com.nike.innovation.android.bigfoot.ble.model.device.BigfootDeviceLEDIntensity
import com.nike.innovation.android.bigfoot.ble.model.user.BigfootUser

private const val PARAM_IS_MULTI_PAIR  = "PREF_IS_MULTI_PAIR"
private const val PARAM_MODES_SAVED  = "PARAM_MODES_SAVED"
private const val PARAM_GOLD_SLOT_ERROR_COUNT  = "PARAM_GOLD_SLOT_ERROR_COUNT"
private const val PARAM_UPGRADE_SLOT_ERROR_COUNT  = "PARAM_UPGRADE_SLOT_ERROR_COUNT"
private const val PARAM_LFT_PROGRESS  = "PARAM_LFT_PROGRESS"
private const val PREF_SHOE_LED_COLOR = "last_known_shoe_color"
private const val PREF_AUTO_FIT = "auto_fit"
private const val PREF_RESTART_IN_PROGRESS = "PREF_RESTART_IN_PROGRESS"
private const val PREF_BIGFOOT_USER = "PREF_BIGFOOT_USER"
private const val PREF_HAS_SEEN_SPLASH_SCREEN = "PREF_HAS_SEEN_SPLASH_SCREEN"
private const val PREF_FIRMWARE_UPDATE_COMPLETE_TIME = "PREF_FIRMWARE_UPDATE_COMPLETE_TIME"
private const val PREF_FIRMWARE_UPDATE_IN_PROGRESS = "PREF_FIRMWARE_UPDATE_IN_PROGRESS"
private const val PREF_FIRMWARE_UPDATE_RETRY_AFTER_FAILURE = "PREF_FIRMWARE_UPDATE_RETRY_AFTER_FAILURE"

interface AdaptPreferenceHelper {

    var hasSeenSplashScreen: Boolean
    var autofit: Boolean
    var firmwareUpdateCompleteTime: Long
    var isFirmwareRestartInProgress: Boolean
    var isFirmwareUpateInProgress: Boolean
    var isFirmwareUpateRetryAfterFailure: Boolean

    // This is to keep track of multi pair flow. It is only used to fire analytics in the multi pairing flow for now.
    // The reason we had to add a separate flag and not use the type in BigfootOnBoardingPreferenceHelper is  as follows : on boarding type of normal(first time) and add pair(multi pairs)
    // converges into a type normal in the flow.Also the BigfootOnBoardingPreferenceHelper gets nuked at various stages in the flow and we have fore analytics at a later point in on boarding flow where we need to
    // know if it is a multi pair flow or the very first time.
    fun isMultiPairFlow() : Boolean
    fun setMultiPairFlow(isMultiPair : Boolean)

    // This is set to true whenever modes are saved. The next time we land on the lacing screen we will show the "Modes Saved" animation(s) and set isModesSaved to false.
    fun isModesSaved() : Boolean
    fun setModesSaved(isModesSaved : Boolean)

    //Count of no of time we encounter gold slot errors after lft
    fun getGoldSlotErrorCountAfterLft(): Int
    fun setGoldSlotErrorCountAfterLft(count:Int)

    //Count of no of time we encounter upgrade slot errors after lft
    fun getUpgradeSlotErrorCountAfterLft(): Int
    fun setUpgradeSlotErrorCountAfterLft(count:Int)

    //Count of no of time we encounter during lft
    fun getLftProgressErrorCount(): Int
    fun setLftProgressErrorCount(count:Int)

    fun getShoeLedColor(): BigfootDeviceColor
    fun saveShoeLedColor(ledLight: BigfootDeviceColor)

    fun getBigfootUser(adaptResult: AdaptResultListener<BigfootUser>)
    fun getBigfootUser(): BigfootUser?
    fun setBigfootUser(bigfootUser: BigfootUser)

    fun nuke()

    companion object {
        fun get(): AdaptPreferenceHelper {
            return AdaptPreferenceHelperImpl
        }
    }
}

private object AdaptPreferenceHelperImpl : AdaptPreferenceHelper {

    override var hasSeenSplashScreen: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_HAS_SEEN_SPLASH_SCREEN, false)
        }
        set(value) {
            sharedPreferences.edit() {
                putBoolean(PREF_HAS_SEEN_SPLASH_SCREEN, value)
            }
        }

    override var autofit: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_AUTO_FIT, false)
        }
        set(value) {
            sharedPreferences.edit() {
                putBoolean(PREF_AUTO_FIT, value)
            }
        }

    override var firmwareUpdateCompleteTime: Long
        get() {
            return sharedPreferences.getLong(PREF_FIRMWARE_UPDATE_COMPLETE_TIME, 0L)
        }
        set(value) {
            sharedPreferences.edit() {
                putLong(PREF_FIRMWARE_UPDATE_COMPLETE_TIME, value)
            }
        }



    override var isFirmwareRestartInProgress: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_RESTART_IN_PROGRESS, false)
        }
        set(value) {
            sharedPreferences.edit {
                putBoolean(PREF_RESTART_IN_PROGRESS, value)
            }
        }

    override var isFirmwareUpateInProgress: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_FIRMWARE_UPDATE_IN_PROGRESS, false)
        }
        set(value) {
            sharedPreferences.edit {
                putBoolean(PREF_FIRMWARE_UPDATE_IN_PROGRESS, value)
            }
        }

    override var isFirmwareUpateRetryAfterFailure: Boolean
        get() {
            return sharedPreferences.getBoolean(PREF_FIRMWARE_UPDATE_RETRY_AFTER_FAILURE, false)
        }
        set(value) {
            sharedPreferences.edit {
                putBoolean(PREF_FIRMWARE_UPDATE_RETRY_AFTER_FAILURE, value)
            }
        }

    private val sharedPreferences: SharedPreferences by lazy {
        BigfootApplication.APPLICATION.getSharedPreferences("adapt_preferences", Context.MODE_PRIVATE)
    }

    override fun isMultiPairFlow() : Boolean {
      return  sharedPreferences.getBoolean(PARAM_IS_MULTI_PAIR, false)
    }

    override fun setMultiPairFlow(isMultiPair: Boolean) {
        sharedPreferences.edit {
            putBoolean(PARAM_IS_MULTI_PAIR, isMultiPair)
        }
    }

    override fun isModesSaved() : Boolean {
        return  sharedPreferences.getBoolean(PARAM_MODES_SAVED, false)
    }

    override fun setModesSaved(isModesSaved: Boolean) {
        sharedPreferences.edit {
            putBoolean(PARAM_MODES_SAVED, isModesSaved)
        }
    }

    //TODO -- These 3 error counts are now being removed with the nuke method. Make sure this isn't leading to problems.

    override fun getGoldSlotErrorCountAfterLft(): Int {
        return  sharedPreferences.getInt(PARAM_GOLD_SLOT_ERROR_COUNT, 0)
    }

    override fun setGoldSlotErrorCountAfterLft(count: Int) {
        sharedPreferences.edit{
            putInt(PARAM_GOLD_SLOT_ERROR_COUNT,count)
        }
    }

    override fun getUpgradeSlotErrorCountAfterLft(): Int {
        return  sharedPreferences.getInt(PARAM_UPGRADE_SLOT_ERROR_COUNT, 0)
    }

    override fun setUpgradeSlotErrorCountAfterLft(count: Int) {
        sharedPreferences.edit{
            putInt(PARAM_UPGRADE_SLOT_ERROR_COUNT,count)
        }
    }

    override fun getLftProgressErrorCount(): Int {
        return  sharedPreferences.getInt(PARAM_LFT_PROGRESS, 0)
    }

    override fun setLftProgressErrorCount(count: Int) {
        sharedPreferences.edit{
            putInt(PARAM_LFT_PROGRESS,count)
        }
    }

    override fun getShoeLedColor(): BigfootDeviceColor {
        return Gson().fromJson(sharedPreferences.getString(PREF_SHOE_LED_COLOR, null),
                BigfootDeviceColor::class.java)
                ?: BigfootDeviceColor(BigfootColorPreset.HERO_BLUE, BigfootDeviceLEDIntensity.HIGHEST)
    }

    override fun saveShoeLedColor(ledLight: BigfootDeviceColor) {
        sharedPreferences.edit {
            ledLight.bigfootColorPreset.run {
                putString(PREF_SHOE_LED_COLOR, Gson().toJson(ledLight))
            }
        }
    }

    override fun getBigfootUser(adaptResult: AdaptResultListener<BigfootUser>) {
        retrieve {
            sharedPreferences.getString(PREF_BIGFOOT_USER, null)?.let {
                Gson().fromJson(it, BigfootUser::class.java)
            }?.run { adaptResult onSuccess this }
                    ?: let { adaptResult onFailure UserDoesNotExistInCacheException() }
        }
    }

    @WorkerThread
    override fun getBigfootUser(): BigfootUser? {
        return sharedPreferences.getString(PREF_BIGFOOT_USER, null)?.let {
            Gson().fromJson(it, BigfootUser::class.java)
        }
    }

    override fun setBigfootUser(bigfootUser: BigfootUser) {
        sharedPreferences.edit {
            putString(PREF_BIGFOOT_USER, Gson().toJson(bigfootUser))
        }
    }

    override fun nuke() {
        sharedPreferences.edit().clear().commit()
    }
}

private fun retrieve(retrieve: () -> Unit) {
    AdaptExecutor.IO.execute(retrieve)
}
