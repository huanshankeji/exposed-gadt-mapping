import com.huanshankeji.CommonDependencies
import com.huanshankeji.CommonVersions

val projectVersion = "0.3.1-SNAPSHOT"

// don't use a snapshot version in a main branch
// TODO remove Exposed's explicit version when migration to Exposed 1.0.0 is complete
val commonVersions = CommonVersions(kotlinCommon = "0.6.1", exposed = "0.61.0")
val commonDependencies = CommonDependencies(commonVersions)
