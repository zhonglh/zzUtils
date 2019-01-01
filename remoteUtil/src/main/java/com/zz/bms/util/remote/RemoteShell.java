package com.zz.bms.util.remote;


import net.neoremind.sshxcute.core.ConnBean;
import net.neoremind.sshxcute.core.Result;
import net.neoremind.sshxcute.core.SSHExec;
import net.neoremind.sshxcute.exception.TaskExecFailException;
import net.neoremind.sshxcute.task.impl.ExecCommand;
import org.apache.log4j.Logger;

public class RemoteShell {
	
	static Logger  logger = Logger.getLogger(RemoteShell.class);

 

    private static class SSHExecHolder {
        static SSHExec instance = getSSHExec ();
    }

    public static SSHExec getInstance(){
        if (SSHExecHolder.instance.isConnected ()) {
            return SSHExecHolder.instance;
        } else {
            SSHExecHolder.instance.disconnect ();
            SSHExecHolder.instance = getSSHExec ();
        }
        return SSHExecHolder.instance;
    }

    private static SSHExec getSSHExec(){
        ConnBean cb = new ConnBean (ShellConstant.SHELL_HOST,ShellConstant.SHELL_HOST_NAME,ShellConstant.SHELL_HOST_PASSWD);
        SSHExec ssh = new SSHExec (cb);
        ssh.connect ();
        return ssh;
    }

    public static Result exec(String cmd) throws TaskExecFailException{
    	SSHExec execer = RemoteShell.getInstance ();
    	logger.info("---------------------------");
    	Result result = execer.exec (new ExecCommand (cmd));
    	logger.info("---------------------------");
    	return result;
    }
    
    

    public static void shutSSHService(){
    	SSHExecHolder.instance.disconnect ();
    }
    
    
    


    
    
    
    
    
    
    public static SSHExec getSSHExec(String ip, String user, String password){
    	ConnBean cb = new ConnBean(ip, user, password);
    	SSHExec ssh = new SSHExec(cb);
		ssh.connect();
		return ssh;
    }
    
    
    public static Result exec(SSHExec execer,String cmd) throws TaskExecFailException{
    	logger.info("---------------------------");
    	Result result = execer.exec (new ExecCommand (cmd));
    	logger.info("---------------------------");
    	return result;
    }
    
    

    public static void shutSSHService(SSHExec execer){
    	execer.disconnect ();
    }
    
    
}
