LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://shell.json \
    file://mmr-firmware.bit \
    file://mmr-firmware.dtsi \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

inherit dfx_user_dts

DEPENDS += "mmr-firmware-prepare"