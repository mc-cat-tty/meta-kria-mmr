LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

python () {
    WORKDIR = d.getVar("WORKDIR")
    files = os.path.join(WORKDIR, "files")
    bitstream, dtsi = [os.path.join(files, f"mmr-firmware.{ext}") for ext in ("bit", "dtsi")]
    os.makedirs(files, exist_ok=True)
    
    try: os.mknod(bitstream)
    except: pass

    try: os.mknod(dtsi)
    except: pass
}

FILESEXTRAPATHS:append = "${WORKDIR}/files"

inherit dfx_user_dts

SRC_URI = "\
    file://shell.json \
    file://mmr-firmware.bit \
    file://mmr-firmware.dtsi \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
PLATFORM_PATH:task-generate-pl-artifacts = "${WORKDIR}/platform/platform.xsa"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${PLATFORM_PATH} -git-branch xlnx_rel_v2022.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"
DTSI_OVERLAY:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"

_BIT_PATH = "${WORKDIR}/files/mmr-firmware.bit"
_DTSI_PATH = "${WORKDIR}/files/mmr-firmware.dtsi"

do_generate_pl_artifacts() {
    mkdir -p ${WORKDIR}/platform
    cp ${HDF_PATH} ${PLATFORM_PATH}
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
    mkdir -p ${WORKDIR}/files
    cp ${BITSTREAM} ${_BIT_PATH}
    cp ${DTSI_OVERLAY} ${_DTSI_PATH}
}

addtask do_generate_pl_artifacts before do_fetch