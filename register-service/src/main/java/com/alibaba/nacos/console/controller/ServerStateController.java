package com.alibaba.nacos.console.controller;

import com.alibaba.nacos.common.utils.VersionUtils;
import com.alibaba.nacos.sys.env.EnvUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Server state controller.
 *
 * @author xingxuechao on:2019/2/27 11:17 AM
 */
@RestController
@RequestMapping("/v1/console/server")
public class ServerStateController {

	/**
	 * Get server state of current server.
	 * @return state json.
	 */
	@GetMapping("/state")
	public ResponseEntity<Map<String, String>> serverState() {
		Map<String, String> serverState = new HashMap<>(4);
		serverState.put("standalone_mode",
				EnvUtil.getStandaloneMode() ? EnvUtil.STANDALONE_MODE_ALONE : EnvUtil.STANDALONE_MODE_CLUSTER);

		serverState.put("function_mode", EnvUtil.getFunctionMode());
		serverState.put("version", VersionUtils.version);

		return ResponseEntity.ok().body(serverState);
	}

}
