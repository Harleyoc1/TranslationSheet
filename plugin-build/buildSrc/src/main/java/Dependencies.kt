object Versions {
    const val JUNIT = "4.13.2"
    const val GOOGLE_API_CLIENT = "1.30.4"
    const val GOOGLE_OAUTH_CLIENT = "1.30.6"
    const val GOOGLE_SHEETS_API = "v4-rev581-1.25.0"
}

object BuildPluginsVersion {
    const val DETEKT = "1.16.0"
    const val KOTLIN = "1.4.32"
    const val KTLINT = "10.0.0"
    const val PLUGIN_PUBLISH = "0.14.0"
    const val VERSIONS_PLUGIN = "0.38.0"
}

object Lib {
    const val GOOGLE_API_CLIENT = "com.google.api-client:google-api-client:${Versions.GOOGLE_API_CLIENT}"
    const val GOOGLE_OAUTH_CLIENT = "com.google.oauth-client:google-oauth-client-jetty:${Versions.GOOGLE_OAUTH_CLIENT}"
    const val GOOGLE_SHEETS_API = "com.google.apis:google-api-services-sheets:${Versions.GOOGLE_SHEETS_API}"
}

object TestingLib {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
}