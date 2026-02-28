#!/bin/bash

# see https://api.adoptium.net/q/swagger-ui/#/Binary/getBinaryByVersion
macApi='https://api.adoptium.net/v3/binary/version/jdk-17.0.14%2B7/mac/aarch64/jdk/hotspot/normal/eclipse?project=jdk'
curl -L -o jdk.tar.gz "${macApi}"
tar -xzf jdk.tar.gz

# Create a custom minimal runtime using jlink instead of shipping the full JDK
jdk-17.0.14+7/Contents/Home/bin/jlink \
  --add-modules java.se,jdk.unsupported,jdk.zipfs,jdk.management,jdk.crypto.ec,jdk.localedata,jdk.charsets \
  --strip-debug --no-man-pages --no-header-files \
  --compress=2 \
  --output jre

if [ $? -ne 0 ]; then
  echo "jlink failed to create custom runtime" >&2
  exit 1
fi

rm -rf jdk-17.0.14+7 jdk.tar.gz
