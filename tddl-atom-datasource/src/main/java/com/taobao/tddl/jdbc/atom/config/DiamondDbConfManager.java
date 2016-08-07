/*(C) 2007-2012 Alibaba Group Holding Limited.	

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.tddl.common.TDDLConstant;
import com.taobao.tddl.common.config.ConfigDataHandler;
import com.taobao.tddl.common.config.ConfigDataHandlerFactory;
import com.taobao.tddl.common.config.ConfigDataListener;
import com.taobao.tddl.common.config.impl.DefaultConfigDataHandlerFactory;
import com.taobao.tddl.jdbc.atom.common.TAtomConstants;

/**
 * ȫ�ֺ�Ӧ�õ����ù���Diamondʵ��
 * 
 * @author qihao
 * 
 */
public class DiamondDbConfManager implements DbConfManager {
	private static Log logger = LogFactory.getLog(DiamondDbConfManager.class);
	private String globalConfigDataId;
	private String appConfigDataId;
	private ConfigDataHandlerFactory configFactory;
	private ConfigDataHandler globalHandler;
	private ConfigDataHandler appDBHandler;
	private volatile List<ConfigDataListener> globalDbConfListener = new ArrayList<ConfigDataListener>();
	private volatile List<ConfigDataListener> appDbConfListener = new ArrayList<ConfigDataListener>();

	public void init() {

	public String getAppDbConfDataId() {
		return appConfigDataId;
	}

	public String getAppDbDbConf() {
		if (null != appDBHandler) {
			return appDBHandler.getData(TDDLConstant.DIAMOND_GET_DATA_TIMEOUT,ConfigDataHandler.FIRST_CACHE_THEN_SERVER_STRATEGY);
		}
		logger.error("[getDataError] appDBConfig not init !");
		return null;
	}

	public String getGlobalDbConf() {
		if (null != globalHandler) {
			return globalHandler.getData(TDDLConstant.DIAMOND_GET_DATA_TIMEOUT,ConfigDataHandler.FIRST_CACHE_THEN_SERVER_STRATEGY);
		}
		logger.error("[getDataError] globalConfig not init !");
		return null;
	}

	public void setGlobalConfigDataId(String globalConfigDataId) {
		this.globalConfigDataId = globalConfigDataId;
	}

	public String getAppConfigDataId() {
		return appConfigDataId;
	}

	public void setAppConfigDataId(String appConfigDataId) {
		this.appConfigDataId = appConfigDataId;
	}

	/**
	 * @param Listener
	 */
	public void registerGlobaDbConfListener(ConfigDataListener listener) {
		globalDbConfListener.add(listener);
	}

	/**
	 * @param Listener
	 */
	public void registerAppDbConfListener(ConfigDataListener listener) {
		appDbConfListener.add(listener);
	}

	public void stopDbConfManager() {
		if (null != this.globalHandler) {
			this.globalHandler.closeUnderManager();
		}
		if (null != this.appDBHandler) {
			this.appDBHandler.closeUnderManager();
		}
	}
}