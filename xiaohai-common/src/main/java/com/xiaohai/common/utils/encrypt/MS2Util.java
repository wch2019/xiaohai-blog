package com.xiaohai.common.utils.encrypt;

import cn.hutool.crypto.ECKeyUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.signers.PlainDSAEncoding;

/**
 * 国密MS2
 * @author wangchenghai
 * @date 2024/02/20 9:24:12
 */
@Slf4j
public class MS2Util {
    public static void main(String[] args) {
        String publicKey = "04ec81090e9c6156a568a012b74f1eb9bd0dc8760e77a8c6d851292911f6ac841bb3efd3f13676d9c662599f5814f76dd821daab9b4c0db8375954a5a07e44e992";

        SM2 sm2 = new SM2(null,ECKeyUtil.toSm2PublicParams(publicKey));
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        sm2.setEncoding(new PlainDSAEncoding());

        String msg="QDLX230169";
        String decrypt1 = sm2.encryptHex(msg, KeyType.PublicKey);
        msg="LIUyike123";
        String decrypt2 = sm2.encryptHex(msg, KeyType.PublicKey);
        log.info(decrypt1);
        log.info(decrypt2);
    }
}
