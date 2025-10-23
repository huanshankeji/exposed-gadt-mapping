import com.huanshankeji.CommonDependencies
import com.huanshankeji.CommonVersions

val projectVersion = "0.4.0-SNAPSHOT"

// TODO don't use a snapshot version in a main branch
val commonVersions = CommonVersions(kotlinCommon = "0.7.0-SNAPSHOT")
val commonDependencies = CommonDependencies(commonVersions)
