DESCRIPTION = "Init script to load kernel module"
SUMMARY = "Init script for Kria SOM to to load kernel module"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://load-module.sh \
    file://load-module.service \
"

inherit update-rc.d systemd

INSANE_SKIP:${PN} += "installed-vs-shipped"

INITSCRIPT_NAME = "load-module.sh"
INITSCRIPT_PARAMS = "start 99 S ."

SYSTEMD_PACKAGES="${PN}"
SYSTEMD_SERVICE:${PN}="load-module.service"
SYSTEMD_AUTO_ENABLE:${PN}="enable"

COMPATIBLE_MACHINE = "^$"
COMPATIBLE_MACHINE:kria = "${MACHINE}"

do_install () {

    if [ -n "${MODULE_NAME}" ]; then
        sed -i -e 's/@@MODULE_NAMES@@/${MODULE_NAME}/' ${WORKDIR}/load-module.sh
    else
        bbwarn "MODULE_NAME variable not declared."
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d/
        install -m 0755 ${WORKDIR}/load-module.sh ${D}${sysconfdir}/init.d/
    fi

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/load-module.sh ${D}${bindir}/
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/load-module.service ${D}${systemd_system_unitdir}

    install -d ${D}/exec.d/
    install -m 0755 ${WORKDIR}/load-module.sh ${D}/exec.d/

}

FILES:${PN} = "${@bb.utils.contains('DISTRO_FEATURES','sysvinit','${sysconfdir}/init.d/load-module.sh', '', d)} /exec.d/load-module.sh"
