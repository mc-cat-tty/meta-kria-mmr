DESCRIPTION = "xmutil"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

REPO = "git://github.com/Xilinx/xmutil.git;protocol=https"
SRCREV = "13b5dafec47f5e10dfb6106faec9a13f4914d270"
BRANCH = "master"
BRANCHARG = "${@['nobranch=1', 'branch=${BRANCH}'][d.getVar('BRANCH', True) != '']}"
SRC_URI="${REPO};${BRANCHARG}"

S = "${WORKDIR}/git"

RDEPENDS:${PN} = " \
	python3-core \
	python3-distro \
	python3-periphery \
	freeipmi \
	dfx-mgr \
	xlnx-platformstats \
	axi-qos \
	ddr-qos \
	image-update \
	"

inherit python3-dir autotools-brokensep

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:zynqmp = "zynqmp"
COMPATIBLE_MACHINE:versal = "versal"

RREPLACES:${PN} = "kria-pwrctl"

FILES:${PN} += "${bindir}/xmutil"