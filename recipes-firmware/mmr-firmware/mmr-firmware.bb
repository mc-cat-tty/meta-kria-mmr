LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit dfx_user_dts

SRC_URI := "file://shell.json"

SRC_URI:append = "\
    file://mmr-firmware.bit \
    file://mmr-firmware.dtsi \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

RDEPENDS_${PN} += "mmr-firmware-prepare:do_generate_pl_artifacts"
DEPENDS += "mmr-firmware-config"