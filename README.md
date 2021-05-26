# scala-pbm

Minimal API library (actually single class) to create PBM images either as plain or in compressed (binary) form.

## Install using sbt

You need to add the dependency
```aidl
    "de.halcony"                 %% "scala-argparse"                % "(version)"
```

as well as the resolver

```aidl
resolvers ++= Seq(
    "Sonatype OSS Snapshots" at "https://s01.oss.sonatype.org/content/repositories/public",
)
```


## Usage

```aidl
PbmImage(
        Array(
          Array(1,0,0,0,0,0,0,1,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,0,0,0,0,0,1,1)
        )
      ).writeToFile("/tmp/uncompressed.pbm",compressed = false)
```