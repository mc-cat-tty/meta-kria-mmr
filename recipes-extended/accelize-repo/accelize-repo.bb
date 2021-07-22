DESCRIPTION = "accelize repo file installer"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://accelize.repo"

do_install() {
	install -d ${D}${sysconfdir}/yum.repos.d
	install -m 0755 ${WORKDIR}/accelize.repo ${D}${sysconfdir}/yum.repos.d/
}

pkg_postinst_ontarget_${PN}() {
	sed -i ${sysconfdir}/yum.repos.d/accelize.repo -e 's,enabled=0,enabled=1,g'
}

FILES_${PN} = "${sysconfdir}/yum.repos.d/accelize.repo"
