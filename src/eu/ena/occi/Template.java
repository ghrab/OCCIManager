package eu.ena.occi;

public class Template extends Compute {

	public Template() {
	}

}

/*
 * TPL_CATEGORY='compute;
 * scheme="http://schemas.ogf.org/occi/infrastructure#";class="kind";'
 * TPL_ATTRIBUTE='occi.core.title="ttylinux",'
 * TPL_ATTRIBUTE+='occi.core.summary"A short summary",'
 * TPL_ATTRIBUTE+='occi.compute.architecture="x64",'
 * TPL_ATTRIBUTE+='occi.compute.cores=1,' TPL_ATTRIBUTE+='occi.compute.memory=4'
 * TPL_LINK="<${NETWORK_LOCATION#*$URI}>"
 * ';rel="http://schemas.ogf.org/occi/infrastructure#network";category="http://schemas.ogf.org/occi/core#link";,'
 * TPL_LINK+="<${STORAGE_LOCATION#*$URI}>"
 * ';rel="http://schemas.ogf.org/occi/infrastructure#storage";category="http://schemas.ogf.org/occi/core#link";'
 */