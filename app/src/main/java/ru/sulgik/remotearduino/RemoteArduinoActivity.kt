package ru.sulgik.remotearduino

import android.Manifest
import android.app.Activity
import android.content.*
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import ru.sulgik.remotearduino.auth.RegistrationActivity
import ru.sulgik.remotearduino.auth.RegistrationActivity.Companion.REGISTRATION_ACTIVITY_TAG
import ru.sulgik.remotearduino.bluetooth.requestdevice.DevicesInitActivity
import ru.sulgik.remotearduino.database.FireVM
import ru.sulgik.remotearduino.log.SystemLog
import ru.sulgik.remotearduino.permission.MaterialPermission
import ru.sulgik.remotearduino.permission.MaterialPermission.Companion.IMPORTANT
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class RemoteArduinoActivity  (@LayoutRes val resId : Int): AppCompatActivity(resId) {


    protected val db: FireVM by viewModels()
    abstract val TAG: String

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun goToLogIn() {
        startActivity(Intent(this, RegistrationActivity::class.java))
    }

    interface ActivityCallback {

        @CallSuper
        fun onFinish(state: Int) {
            when (state) {
                Activity.RESULT_OK -> onCompleted()
                Activity.RESULT_CANCELED -> onCanceled()
                else -> onOther(state)
            }
        }

        fun onCanceled()

        fun onCompleted()

        fun onOther(state: Int) {}

    }

    interface FragmentCallback {

        @CallSuper
        fun toFragment(task: String) {
            when (task) {
                FINISH_FRAGMENT -> toFinishFragment()
            }
        }

        fun toFinishFragment()


    }

    @MainThread
    protected fun <T : ViewDataBinding> viewDataBindings(@LayoutRes resId: Int) =
        DataBindingProperty<T>(resId)

    protected class DataBindingProperty<out T : ViewDataBinding>(@LayoutRes private val resId: Int) :
        ReadOnlyProperty<Activity, T> {
        private var binding: T? = null

        override fun getValue(thisRef: Activity, property: KProperty<*>): T =
            binding ?: DataBindingUtil.setContentView<T>(thisRef, resId).also {
                binding = it
            }
    }

    companion object {
        const val FINISH_FRAGMENT = "finish_fragment:198461"

        fun createSpring(
            view: View,
            property: DynamicAnimation.ViewProperty,
            finalPosition: Float,
            stiffness: Float,
            ratio: Float
        ) = SpringAnimation(view, property).apply {
            spring = SpringForce(finalPosition).apply {
                this.stiffness = stiffness
                dampingRatio = ratio
            }
        }

        val log = SystemLog("GeneralActivityClass")

        fun getBluetoothDiscoveringPermissions(context: Context) = arrayOf(
            MaterialPermission(
                context = context,
                path = Manifest.permission.ACCESS_FINE_LOCATION,
                name = R.string.location,
                description = R.string.location_description,
                logo = R.drawable.ic_location_on_black_24dp,
                logo2 = R.drawable.ic_location_off_black_24dp,
                necessary = IMPORTANT
            ),
            MaterialPermission(
                context = context,
                path = Manifest.permission.BLUETOOTH,
                name = R.string.bluetooth,
                description = R.string.bluetooth_description,
                logo = R.drawable.ic_settings_bluetooth_black_24dp,
                necessary = IMPORTANT
            )
        )

    }
}