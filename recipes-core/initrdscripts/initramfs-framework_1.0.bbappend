FILESEXTRAPATHS:append := "${THISDIR}/${PN}"

SRC_URI += "file://loadkernelmodule \
	  "

do_install:append() {

	if [ -n "${MODULE_NAME}" ]; then
        	sed -i -e 's/@@MODULE_NAMES@@/${MODULE_NAME}/' ${WORKDIR}/loadkernelmodule
    	else
        	bbwarn "MODULE_NAME variable not declared."
    	fi
	
	install -m 0755 ${WORKDIR}/loadkernelmodule ${D}/init.d/11-loadkernelmodule
}

PACKAGES += " \
	initramfs-module-loadkernelmodule \
	"

SUMMARY:initramfs-module-loadkernelmodule = "search for the kernel module and load it"
RDEPENDS:initramfs-module-loadkernelmodule = "${PN}-base"
FILES:initramfs-module-loadkernelmodule = "/init.d/11-loadkernelmodule"

