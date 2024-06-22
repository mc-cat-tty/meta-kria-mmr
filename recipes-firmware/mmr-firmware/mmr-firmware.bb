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

PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${HDF_PATH} -git-branch xlnx_rel_v2022.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"
DTSI_OVERLAY:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"

do_generate_pl_artifacts() {
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
    mkdir -p ${WORKDIR}/files
    cp ${BITSTREAM} ${WORKDIR}/files/mmr-firmware.bit
    cp ${DTSI_OVERLAY} ${WORKDIR}/files/mmr-firmware.dtsi
}

do_configure:prepend() {
    SRC_URI = d.getVar("SRC_URI")
    WORKDIR = d.getVar("WORKDIR")
    SRC_URI = (
        SRC_URI
            .replace("file://mmr-firmware.bit", f"file://{WORKDIR}/files/mmr-firmware.bit")
            .replace("file://mmr-firmware.dtsi", f"file://{WORKDIR}/files/mmr-firmware.dtsi")
    )
    bb.warn(SRC_URI)
    d.setVar("SRC_URI", SRC_URI)
}

addtask do_generate_pl_artifacts before do_configure