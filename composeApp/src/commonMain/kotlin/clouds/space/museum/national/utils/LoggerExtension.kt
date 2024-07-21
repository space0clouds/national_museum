package clouds.space.museum.national.utils

import co.touchlab.kermit.Logger

private const val TAG = "space0clouds"

internal fun Logger.debug(throwable: Throwable? = null, message: () -> String) =
    withTag(TAG).d(throwable = throwable, message = message)