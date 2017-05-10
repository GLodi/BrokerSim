/*
 * Copyright 2017 GLodi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package giuliolodi.brokersim.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import giuliolodi.brokersim.di.component.ActivityComponent

open class BaseFragment : DialogFragment(), BaseContract.View {

    private lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity)
            mActivity = context
    }

    override fun isNetworkAvailable(): Boolean {
        return mActivity.isNetworkAvailable()
    }

    fun getActivityComponent(): ActivityComponent {
        return mActivity.getActivityComponent()
    }

}