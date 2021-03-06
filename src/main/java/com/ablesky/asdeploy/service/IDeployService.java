package com.ablesky.asdeploy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ablesky.asdeploy.pojo.ConflictDetail;
import com.ablesky.asdeploy.pojo.DeployItem;
import com.ablesky.asdeploy.pojo.DeployLock;
import com.ablesky.asdeploy.pojo.DeployRecord;
import com.ablesky.asdeploy.pojo.PatchGroup;
import com.ablesky.asdeploy.pojo.Project;
import com.ablesky.asdeploy.util.Page;

public interface IDeployService {

	DeployLock checkCurrentLock();

	void saveOrUpdateDeployLock(DeployLock lock);

	void saveOrUpdateDeployRecord(DeployRecord record);

	void unlockDeploy();

	DeployItem getDeployItemByFileNameAndProjectIdAndVersion(String fileName,Long projectId, String version);

	DeployItem persistDeployItem(MultipartFile deployItemFile, Project project, PatchGroup patchGroup, DeployRecord deployRecord, String deployType, String version) throws IllegalStateException, IOException;

	DeployRecord getDeployRecordById(Long id);

	Page<DeployRecord> getDeployRecordPaginateResult(int start, int limit, Map<String, Object> param);

	void saveOrUpdateDeployItem(DeployItem item);

	void persistInfoBeforeDeployStart(DeployRecord deployRecord, PatchGroup patchGroup, List<String> filePathList, String deployManner);

	List<ConflictDetail> getConflictDetailListResultByParam(int start, int limit, Map<String, Object> param);

	List<ConflictDetail> getConflictDetailListResultByParam(Map<String, Object> param);

}
