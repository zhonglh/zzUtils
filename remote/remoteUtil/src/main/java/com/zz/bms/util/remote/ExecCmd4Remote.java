package com.zz.bms.util.remote;

import net.neoremind.sshxcute.core.Result;
import net.neoremind.sshxcute.core.SSHExec;
import org.apache.log4j.Logger;

public class ExecCmd4Remote {

	public static Logger logger = Logger.getLogger(ExecCmd4Remote.class);

	/**
	 * @param cmd
	 *            传入 调用脚本的命令
	 * @return 返回 流中的数据
	 */
	public static String runtime(String ip, String user, String password, String cmd) {

		SSHExec execer = null;
		
		try {

			execer = RemoteShell.getSSHExec(ip, user, password);
			
			Result r = RemoteShell.exec(execer, cmd);
			String err = r.error_msg;
			
			if (null != err && !"".equals(err)) {
				//错误流信息
				logger.debug("errBuffer-----------" + err.toString());
				throw new RuntimeException(err.toString());
			}

			String stdin = r.sysout;
			StringBuffer stdinBuffer = null;
			if (stdin != null) {
				stdinBuffer = new StringBuffer(r.sysout);
			}
			if (stdinBuffer != null && stdinBuffer.toString().length() > 0) {
				return stdinBuffer.toString();
			} else {
				return "";
			}
			
		} catch (Exception e) {
			logger.error(e.toString(), e);
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			try {
				if (execer != null) {
					execer.disconnect();
					execer = null;
				}
			} catch (Exception e) {

			}
		}
	}

}
