package com.zz.bms.util.remote;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ExecCmd4Linux {

    public static Logger        logger        = Logger.getLogger (ExecCmd4Linux.class);



    /**
     * @param cmd
     *            传入 调用脚本的命令
     * @return 返回 流中的数据
     */
    public static String Runtime(String[] cmd) throws RuntimeException {

    	logger.info("cmd : "+cmd[0]);
    	logger.info("cmd : "+cmd[1]);
    	logger.info("cmd : "+cmd[2]);
    	
        final StringBuffer errBuffer = new StringBuffer();
        // 正确流
        InputStreamReader getInput = null;
        try {
            Runtime runtime = Runtime.getRuntime ();
            Process process = runtime.exec (cmd);
            if (process != null) {
                /** 创建错误流 */
                final InputStreamReader errInput = new InputStreamReader(process.getErrorStream ());

                ErrorStreamThread errThread = new ErrorStreamThread () {

                    @Override
                    public void run(){
                        BufferedReader errReader = new BufferedReader(errInput);
                        try {
                            String line = null;
                            while ((line = errReader.readLine ()) != null) {
                                errBuffer.append (line);
                            }
                        } catch (IOException e) {
                            logger.error (e.toString (), e);
                            this.setException (new RuntimeException(e.getMessage ()));
                        } finally {
                            // 关闭错误流
                            if (errInput != null) {
                                try {
                                    errInput.close ();
                                } catch (IOException e) {
                                    logger.error ("errInput close", e);
                                    this.setException (new RuntimeException(e.getMessage ()));
                                }
                            }
                        }
                    }
                };
                errThread.start ();

                getInput = new InputStreamReader(process.getInputStream ());
                BufferedReader getInputBUffer = new BufferedReader(getInput);
                StringBuffer stdinBuffer = new StringBuffer();
                String line;
                while ((line = getInputBUffer.readLine ()) != null) {
                    if (line.length () > 0) {
                        stdinBuffer.append (line);
                        stdinBuffer.append ("\r\n");
                    }
                }

                try {
                    // 正常流读取结束后，合并异常流的子线程
                    errThread.join ();
                } catch (InterruptedException e) {
                    logger.error ("join the child thread appeared exception:", e);
                    throw new RuntimeException(e.getMessage ());
                }

                if (errThread.hasVMException ()) { 
                	// 子线程中发生的异常
                    throw errThread.getException ();
                }

                if (errBuffer != null && !"".equals(errBuffer.toString ())) {
                    //错误流信息
                    logger.debug ("errBuffer-----------" + errBuffer.toString ());
                    throw new RuntimeException(errBuffer.toString ());
                }

                if (stdinBuffer != null && stdinBuffer.toString ().length () > 0) { 
                	logger.info("cmd : "+cmd+"   ; resutlt===\n"+stdinBuffer.toString ());
                	return stdinBuffer.toString (); 
                }
            }
        } catch (IOException e) {
            logger.error (e.toString (), e);
            throw new RuntimeException(e.getMessage (),e);
        } finally {
            // 关闭正确流
            if (getInput != null) {
                try {
                    getInput.close ();
                } catch (IOException e) {
                    logger.error ("errInput close", e);
                }
            }

        }

    	logger.info("cmd : "+cmd+"   ; resutlt= ");
        return "";
    }



    /**
     * 该方法只验证错误流，如果有操作系统错误，就抛出
     * 
     * @param cmd
     * @throws RuntimeException
     */
    public static String validateErrorStream(String[] cmd) throws RuntimeException {
        String data = Runtime (cmd);
        return data;
       
    }

    
    
    private static class ErrorStreamThread extends Thread {

        private RuntimeException e;

        protected void setException(RuntimeException e){
            this.e = e;
        }

        public RuntimeException getException(){
            return e;
        }

        public boolean hasVMException(){
            return e != null;
        }

    }
    
}
