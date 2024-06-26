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
inherit update-alternatives

SRC_URI = "\
    file://shell.json \
    file://mmr-firmware.bit \
    file://mmr-firmware.dtsi \
"

COMPATIBLE_MACHINE ?= "^$"
COMPATIBLE_MACHINE:k26-smk = "k26-smk"

XSA_BASENAME:task-generate-pl-artifacts = "$(basename ${HDF_PATH} .xsa)"
PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
PLATFORM_PATH:task-generate-pl-artifacts = "${WORKDIR}/platform/${XSA_BASENAME}.xsa"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${PLATFORM_PATH} -git-branch xlnx_rel_v2022.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"
DTSI_OVERLAY:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/hw/${XSA_BASENAME}.bit"

_BIT_PATH = "${WORKDIR}/files/mmr-firmware.bit"
_DTSI_PATH = "${WORKDIR}/files/mmr-firmware.dtsi"

do_generate_pl_artifacts() {
    install -d ${WORKDIR}/platform
    cp ${HDF_PATH} ${PLATFORM_PATH}
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
    install -d ${WORKDIR}/files
    cp ${BITSTREAM} ${_BIT_PATH}
    cp ${DTSI_OVERLAY} ${_DTSI_PATH}
}

do_install:append() {
	install -d ${D}${sysconfdir}/dfx-mgrd
	echo "${PN}" > ${D}${sysconfdir}/dfx-mgrd/${PN}
}

FILES:${PN} += "${sysconfdir}"

ALTERNATIVE:${PN} = "default_firmware"
ALTERNATIVE_TARGET[default_firmware] = "${sysconfdir}/dfx-mgrd/${PN}"
ALTERNATIVE_LINK_NAME[default_firmware] = "${sysconfdir}/dfx-mgrd/default_firmware"

addtask do_generate_pl_artifacts before do_fetch
