package com.alibaba.nacos.console.model;

/**
 * all namespace info.
 *
 * @author Nacos
 */
public class NamespaceAllInfo extends Namespace {

	public NamespaceAllInfo(String namespace, String namespaceShowName, int quota, int configCount, int type,
			String namespaceDesc) {
		super(namespace, namespaceShowName, namespaceDesc, quota, configCount, type);
	}

}
