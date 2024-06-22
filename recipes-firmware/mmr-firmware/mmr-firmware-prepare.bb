LICENSE = "MIT"

PLATFORM_NAME = "kr260_custom_platform"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${HDF_PATH} -git-branch xlnx_rel_v2022.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"
DTSI_OVERLAY:task-install = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-install = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"

do_generate_pl_artifacts() {
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
}

do_install:append() {
    mkdir -p ${WORKDIR}/files
    cp ${BITSTREAM} ${WORKDIR}/files/mmr-firmware.bit
    cp ${DTSI_OVERLAY} ${WORKDIR}/files/mmr-firmware.dtsi

}

python do_update_src_uri() {
    SRC_URI = d.getVar("SRC_URI")
    SRC_URI = (
        SRC_URI
            .replace("file://mmr-firmware.bit", "file://${WORKDIR}/files/mmr-firmware.bit")
            .replace("file://mmr-firmware.dtsi", "file://${WORKDIR}/files/mmr-firmware.dtsi")
    )
    d.setVar("SRC_URI", SRC_URI)
}

addtask do_generate_pl_artifacts after do_configure before do_install
addtask do_update_src_uri after do_install