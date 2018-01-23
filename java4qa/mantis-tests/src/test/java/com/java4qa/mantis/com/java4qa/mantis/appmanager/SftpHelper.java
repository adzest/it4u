package com.java4qa.mantis.com.java4qa.mantis.appmanager;

import com.jcraft.jsch.*;
import org.testng.reporters.FileStringBuffer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SftpHelper {
  private final ApplicationManager app;
  private JSch ssh;


  public SftpHelper(ApplicationManager app) {
    this.app = app;
    ssh = new JSch();
  }

  public void upload(File file, String target, String backup) throws IOException {
    Session session = null;
    Channel channel = null;
    try {
//      ssh.setKnownHosts("known_hosts_path");
      session = ssh.getSession(app.getProperty("ftp.login"), app.getProperty("ftp.host"), 22);
      session.setConfig("StrictHostKeyChecking", "no");
      session.setPassword(app.getProperty("ftp.password"));
      session.connect();
      channel = session.openChannel("sftp");
      channel.connect();
      ChannelSftp sftp = (ChannelSftp) channel;
     // System.out.println(new File("file.txt").getAbsolutePath());
      sftp.rm(backup);
      sftp.rename(target, backup);
      sftp.put(new FileInputStream(file), target);
//      sftp.chmod(755, target);
      sftp.rename(target, backup);
    } catch (JSchException e) {
      e.printStackTrace();
    } catch (SftpException e) {
      e.printStackTrace();
    } finally {
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
    }
  }

  //  public void upload(File file, String target, String backup) throws IOException {
//    ftp.connect(app.getProperty("ftp.host"));
//    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
//    ftp.deleteFile(backup);
//    ftp.rename(target, backup);
//    ftp.enterLocalPassiveMode();
//    ftp.storeFile(target, new FileInputStream(file));
//    ftp.rename(target, backup);
//  }
//
  public void restore(String backup, String target) {
    Session session = null;
    Channel channel = null;
    try {
//      ssh.setKnownHosts(target);
      session = ssh.getSession(app.getProperty("ftp.login"), app.getProperty("ftp.host"), 22);
      session.setPassword(app.getProperty("ftp.password"));
      session.connect();
      channel = session.openChannel("sftp");
      channel.connect();
      ChannelSftp sftp = (ChannelSftp) channel;
      sftp.rm(target);
      sftp.rename(backup, target);
      sftp.rename(target, backup);
    } catch (JSchException e) {
      e.printStackTrace();
    } catch (SftpException e) {
      e.printStackTrace();
    } finally {
      if (channel != null) {
        channel.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
    }
  }
}
