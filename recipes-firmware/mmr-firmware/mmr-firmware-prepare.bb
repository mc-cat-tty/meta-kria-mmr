LICENSE = "MIT"

PLATFORM_NAME:task-generate-pl-artifacts = "kr260_custom_platform"
DTSI_OVERLAY:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/psu_cortexa53_0/device_tree_domain/bsp/pl.dtsi"
BITSTREAM:task-generate-pl-artifacts = "${TMPDIR}/${PLATFORM_NAME}/hw/platform.bit"
XSCT_CMD:task-generate-pl-artifacts = "createdts -hw ${HDF_PATH} -git-branch xlnx_rel_v2023.2 -platform-name ${PLATFORM_NAME} -local-repo ${REPOS_PATH} -overlay -out ${TMPDIR}"

do_generate_pl_artifacts() {
    ${XSCT_LOADER} -eval "${XSCT_CMD}"
    mkdir -p ${WORKDIR}/files
    cp ${BITSTREAM} ${WORKDIR}/files/mmr-firmware.bit
    cp ${DTSI_OVERLAY} ${WORKDIR}/files/mmr-firmware.dtsi
}

addtask do_generate_pl_artifacts