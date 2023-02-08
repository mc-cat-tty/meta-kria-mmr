#### Preamble
MACHINEOVERRIDES =. "${@['', 'k26-smk:']['k26-smk' != '${MACHINE}']}"
#### Regular settings follow

YAML_DT_BOARD_FLAGS ?= "{BOARD zynqmp-smk-k26-reva}"

UBOOT_DT_FILES ?= "\
    zynqmp-sck-kr-g-revA.dts \
    zynqmp-sck-kr-g-revB.dts \
    zynqmp-sck-kv-g-revA.dts \
    zynqmp-sck-kv-g-revB.dts \
    "

require conf/machine/include/k26-kria-common.inc

HDF_MACHINE = "k26-smk"

EXTRA_IMAGEDEPENDS += "board-id-data"

INITRAMFS_IMAGE = "petalinux-initramfs-image"

IMAGE_FSTYPES += " ext4 wic wic.xz wic.qemu-sd"

QB_DEFAULT_FSTYPE = "wic.qemu-sd"

INITRAMFS_FSTYPES = "cpio.gz cpio.gz.u-boot tar.gz"
IMAGE_FSTYPES:pn-${INITRAMFS_IMAGE}:zynqmp = "${INITRAMFS_FSTYPES}"
RAMDISK_IMAGE1 = "${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz.u-boot"

EXTRA_IMAGEDEPENDS:remove = "u-boot-xlnx-uenv"
IMAGE_BOOT_FILES = "Image ${INITRAMFS_IMAGE}-${MACHINE}.cpio.gz.u-boot boot.scr"
IMAGE_BOOT_FILES += "devicetree/SMK-*.dtb;devicetree/"
IMAGE_BOOT_FILES += "devicetree/*.dtbo;devicetree/"

#### No additional settings should be after the Postamble
#### Postamble
PACKAGE_EXTRA_ARCHS:append = "${@['', ' k26_smk']['k26-smk' != "${MACHINE}"]}"