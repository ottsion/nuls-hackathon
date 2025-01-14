package com.green.rpc;

import com.green.rpc.vo.Account;
import com.green.utils.Config;
import io.nuls.core.exception.NulsException;
import io.nuls.core.parse.MapUtils;
import io.nuls.core.rpc.info.Constants;
import io.nuls.core.rpc.model.ModuleE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @Author: zhoulijun
 * @Time: 2019-06-12 14:06
 * @Description: 功能描述
 */
@Component
public class AccountTools implements CallRpc {

    @Autowired
    Config config;

    public Account getAccountByAddress(String address) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("chainId", config.getChainId());
        param.put("address", address);
        return callRpc(ModuleE.AC.name, "ac_getAccountByAddress", param, (Function<Map<String, Object>, Account>) res -> {
                    if (res == null) {
                        return null;
                    }
                    return MapUtils.mapToBean(res, new Account());
                }
        );
    }


    /**
     * 账户验证
     * account validate
     *
     * @param chainId
     * @param address
     * @param password
     * @return validate result
     */
    public boolean accountValid(int chainId, String address, String password) throws NulsException {
        Map<String, Object> callParams = new HashMap<>(4);
        callParams.put(Constants.CHAIN_ID, chainId);
        callParams.put("address", address);
        callParams.put("password", password);
        return callRpc(ModuleE.AC.abbr, "ac_getPriKeyByAddress", callParams, (Function<Map<String, Object>, Boolean>) res -> (Boolean) res.get("valid"));
    }


    /**
     * 获取账户私钥
     * account validate
     *
     * @param chainId
     * @param address
     * @param password
     * @return validate result
     */
    public String getAddressPriKey(int chainId, String address, String password) throws NulsException {
        Map<String, Object> callParams = new HashMap<>(4);
        callParams.put(Constants.CHAIN_ID, chainId);
        callParams.put("address", address);
        callParams.put("password", password);
        return callRpc(ModuleE.AC.abbr, "ac_getPriKeyByAddress", callParams, (Function<Map<String, Object>, String>) res -> (String) res.get("priKey"));
    }


}
