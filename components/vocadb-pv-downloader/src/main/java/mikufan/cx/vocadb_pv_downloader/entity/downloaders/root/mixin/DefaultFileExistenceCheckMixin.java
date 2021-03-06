package mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.mixin;

import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatus;
import mikufan.cx.vocadb_pv_downloader.entity.DownloadStatusEnum;
import mikufan.cx.vocadb_pv_downloader.entity.downloaders.root.BaseRequestDownloadValidatePvDownloader;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Default validation to check if the downloaded file exists
 * @author CX无敌
 */
public interface DefaultFileExistenceCheckMixin<Req, Rsp> extends BaseRequestDownloadValidatePvDownloader<Req, Rsp> {

  @Override
  default DownloadStatus validateStatus(String url, Path dir, String fileName, Rsp response, Exception exp){
    org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DefaultFileExistenceCheckMixin.class);
    //file existence check
    var expectedFile = dir.resolve(fileName);
    if (Files.notExists(expectedFile)){
      log.warn("Oh no, can not find the downloaded PV in {}", expectedFile.toAbsolutePath());
      return new DownloadStatus(DownloadStatusEnum.UNKNOWN,
          "Can not find the downloaded PV in " + expectedFile.toAbsolutePath());
    }

    return new DownloadStatus(DownloadStatusEnum.SUCCESS, "Base check from DefaultFileExistenceCheckMixin success");
  }
}
