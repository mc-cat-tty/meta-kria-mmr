LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit dfx_user_dts

SRC_URI = "\
    file://mmr-firmware.bit \
    file://mmr-firmware.dtsi \
    file://shell.json \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
DTSI_OVERLAY:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${HDF_PATH} -git-branch xlnx_rel_v2023.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"

do_generate_pl_artifacts() {
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
    cp ${DTSI_OVERLAY} ./files/mmr-firmware.dtsi
    cp ${BITSTREAM} ./files/mmr-firmware.bit
}

addtask do_generate_pl_artifacts