package com.lele.wechat.vo;

import java.util.HashMap;
import java.util.Map;

// 统一下单
public class WechatPayExtendA {

	private String return_code;
	
	private String return_msg;

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_msg() {
		return return_msg;
	}

	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	
/*	
	public Map<String, Object> getValidParams() {
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("appid", appid);
		params.put("mch_id", mch_id);
		if (device_info != null) params.put("device_info", device_info);
		params.put("nonce_str", nonce_str);
		if (sign_type != null) params.put("sign_type", sign_type);
		params.put("result_code", result_code);
		if (err_code != null) params.put("err_code", err_code);
		if (err_code_des != null) params.put("err_code_des", err_code_des);
		params.put("openid", openid);
		if (is_subscribe != null) params.put("is_subscribe", is_subscribe);
		params.put("trade_type", trade_type);
		params.put("bank_type", bank_type);
		params.put("total_fee", total_fee);
		if (settlement_total_fee != -1) params.put("settlement_total_fee", settlement_total_fee);
		if (fee_type != null) params.put("fee_type", fee_type);
		params.put("cash_fee", cash_fee);
		if (cash_fee_type != null) params.put("cash_fee_type", cash_fee_type);
		params.put("transaction_id", transaction_id);
		params.put("out_trade_no", out_trade_no);
		if (attach != null) params.put("attach", attach);
		params.put("time_end", time_end);
		
		return params;
	}
*/	
	private String appid;
	
	private String mch_id;
	
	private String device_info = null;
	
	private String nonce_str;

	private String sign;
	
	private String sign_type = null;
	
	private String result_code;
	
	private String err_code = null;
	
	private String err_code_des = null;
	
	private String openid;
	
	private String is_subscribe = null;
	
	private String trade_type;
	
	private String bank_type;
	
	private int total_fee;
	
	private int settlement_total_fee = -1;
	
	private String fee_type = null;
	
	private int cash_fee;
	
	private String cash_fee_type = null;

	private String transaction_id;
	
	private String out_trade_no;
	
	private String attach = null;
	
	private String time_end;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getResult_code() {
		return result_code;
	}

	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_code_des() {
		return err_code_des;
	}

	public void setErr_code_des(String err_code_des) {
		this.err_code_des = err_code_des;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getIs_subscribe() {
		return is_subscribe;
	}

	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getBank_type() {
		return bank_type;
	}

	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
	}

	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public int getSettlement_total_fee() {
		return settlement_total_fee;
	}

	public void setSettlement_total_fee(int settlement_total_fee) {
		this.settlement_total_fee = settlement_total_fee;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public int getCash_fee() {
		return cash_fee;
	}

	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}

	public String getCash_fee_type() {
		return cash_fee_type;
	}

	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getTime_end() {
		return time_end;
	}

	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}
}
