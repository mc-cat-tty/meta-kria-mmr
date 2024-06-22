LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "\
    file://shell.json \
    file://mmr-firmware-stub.bit \
    file://mmr-firmware-stub.dtsi \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

inherit dfx_user_dts

PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${HDF_PATH} -git-branch xlnx_rel_v2022.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"

DTSI_OVERLAY_PATH:task-configure = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM_PATH:task-configure = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"

do_generate_pl_artifacts() {
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
}

do_configure:prepend() {
    d.setVar("BIT_PATH", d.getVar("BITSTREAM_PATH"))
    d.setVar("DTSI_PATH", d.getVar("DTSI_OVERLAY_PATH"))
}

addtask do_generate_pl_artifacts before do_configure