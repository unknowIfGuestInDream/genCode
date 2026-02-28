# see https://api.adoptium.net/q/swagger-ui/#/Binary/getBinaryByVersion
$winApi = 'https://api.adoptium.net/v3/binary/version/jdk-17.0.14%2B7/windows/x64/jdk/hotspot/normal/eclipse?project=jdk'
Invoke-WebRequest -Uri $winApi -OutFile 'jdk.zip'
Expand-Archive -Path 'jdk.zip' -DestinationPath '.' -Force

# Create a custom minimal runtime using jlink instead of shipping the full JDK
& '.\jdk-17.0.14+7\bin\jlink.exe' `
  --add-modules java.se,jdk.unsupported,jdk.zipfs,jdk.management,jdk.crypto.ec,jdk.localedata,jdk.charsets `
  --strip-debug --no-man-pages --no-header-files `
  --compress=2 `
  --output jre

if ($LASTEXITCODE -ne 0) {
  throw 'jlink failed to create custom runtime'
}

Remove-Item -Path 'jdk-17.0.14+7' -Recurse -Force
Remove-Item -Path 'jdk.zip' -Force
