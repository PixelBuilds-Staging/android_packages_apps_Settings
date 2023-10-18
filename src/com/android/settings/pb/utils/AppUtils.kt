package com.android.settings.pb.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.os.UserHandle

class AppUtils {
    public fun getCloneableAppList(context: Context): List<PackageInfo> {
        val packageList: List<PackageInfo> =
            context.packageManager.getInstalledPackagesAsUser(/* flags*/ 0, UserHandle.myUserId())

        val filteredList: List<PackageInfo> = packageList.filter {
            val packageName = it.applicationInfo?.packageName
            val isSystemApp = it.applicationInfo?.isSystemApp ?: false
            val isResourceOverlay = it.applicationInfo?.isResourceOverlay ?: false

            packageName?.let { packageName ->
                context.resources.getStringArray(
                    com.android.internal.R.array.cloneable_apps)
                    .asList().contains(packageName)
            } ?: false || (!isSystemApp && !isResourceOverlay)
        }
        return filteredList
    }

    public fun getCloneableAppListStr(context: Context): List<String> {
        return getCloneableAppList(context).map {
            it.packageName
        }
    }
}
