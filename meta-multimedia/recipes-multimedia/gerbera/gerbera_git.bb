Description = "Gerbera - An UPnP media server"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=25cdec9afe3f1f26212ead6bd2f7fac8"

SRC_URI = "git://github.com/v00d00/gerbera.git;protocol=https \
	   file://gerbera.service "


# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "4435b165d121195aefd67d0287f7f9349e181d45"

S = "${WORKDIR}/git"

DEPENDS = "expat zlib curl libupnp e2fsprogs sqlite3 taglib "
RDEPENDS_${PN} = "expat zlib curl libupnp e2fsprogs taglib "

SYSTEMD_SERVICE_${PN} = "gerbera.service"
SYSTEMD_AUTO_ENABLE = "enable"

inherit cmake systemd

EXTRA_OECMAKE = "-DWITH_JS=FALSE -DWITH_TAGLIB=FALSE -DWITH_MAGIC=FALSE -DWITH_EXIF=FALSE -DLIBUUID_INCLUDE_DIRS=${WORKDIR}/recipe-sysroot/usr/include -DLIBUUID_LIBRARIES=-luuid"

do_install_append() {
	install -d ${D}/root/.config/
	install -d ${D}${systemd_system_unitdir}
        install -m 0644 ${WORKDIR}/gerbera.service ${D}${systemd_system_unitdir}/
}

FILES_${PN} += " /root/.config/ \
	         ${systemd_system_unitdir}/* \
"