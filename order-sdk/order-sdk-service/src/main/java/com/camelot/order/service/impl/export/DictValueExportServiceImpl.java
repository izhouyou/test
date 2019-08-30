package com.camelot.order.service.impl.export;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.camelot.order.feign.FeignDictService;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.order.export.vo.DictTypeVO;
import com.camelot.order.export.vo.DictValueVO;
import com.camelot.order.common.OrderConstants;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.service.DictValueExportService;

@Service(value="dictValueExportService")
public class DictValueExportServiceImpl  implements DictValueExportService {
	
	private static Logger log = Log.get(DictValueExportServiceImpl.class);

	@Autowired
	FeignDictService feignDictService;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public List<DictValueVO> getDictValueListByFeign(Integer type) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "DictValueExportServiceImpl-getDictValueListByFeign", "字典Type:" + type);
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<DictValueVO> list = new ArrayList<DictValueVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dictTypeId", type);
		AjaxInfo info = new AjaxInfo();
		info = feignDictService.getDictValue(map);
		if (Utility.isNotEmpty(info)) {
			try {
				mapList = (List<Map<String, Object>>) info.getData();
				String json = JSON.toJSONString(mapList);
				list = JSONObject.parseArray(json, DictValueVO.class);

			} catch (Exception e) {
				Log.error(log, "\n 方法[{}]，异常：[{}]", "DictValueExportServiceImpl-getDictValueListByFeign",
						e.getMessage());
			}

		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "DictValueExportServiceImpl-getDictValueListByFeign",
				JSONObject.toJSONString(list));
		return list;
	}

	@Override
	public String getDictValueNameById(Integer type, Integer id) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "DictValueExportServiceImpl-getDictValueNameById",
				"字典Type:" + type + " ;字典值的ID:" + id);
		String dictName = "";
		// 根据字典类型获取所有值列表
		List<DictValueVO> list = getDictValueListByRedis(type);
		// 遍历列表匹配ID
		if (Utility.isNotEmpty(list) && Utility.isNotEmpty(id)) {
			for (DictValueVO vo : list) {
				Integer i = vo.getDictValueId();
				if (id.equals(vo.getDictValueId())) {
					dictName = vo.getDictValueName();
					break;
				}
			}
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "DictValueExportServiceImpl-getDictValueList", dictName);
		return dictName;
	}

	@Override
	public Long getDictValueIdByNumber(Integer type, String number) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "DictValueExportServiceImpl-getDictValueIdByNumber",
				"字典Type:" + type + " ;字典值的Number:" + number);
		Long id = null;
		// 根据字典类型获取所有值列表
		List<DictValueVO> list = getDictValueListByRedis(type);
		// 遍历列表匹配编码
		if (Utility.isNotEmpty(list) && Utility.isNotEmpty(number)) {
			for (DictValueVO vo : list) {
				if (number.equals(vo.getDictValueNumber())) {
					id = vo.getDictValueId().longValue();
					break;
				}
			}
		}
		Log.debug(log, "\n 方法[{}]，出参：[{}]", "DictValueExportServiceImpl-getDictValueIdByNumber",
				JSONObject.toJSONString(id));
		return id;

	}

	@Override
	public List<DictValueVO> getDictValueListByRedis(Integer type) {
		Log.debug(log, "\n 方法[{}]，入参：[{}]", "DictValueExportServiceImpl-getDictValueListByRedis", "字典Type:" + type);
		List<DictValueVO> list = new ArrayList<DictValueVO>();
		try {
			// Redis载体初始化
			ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
			// 获取字典集合
			Object jsonData = opsForValue.get(OrderConstants.DICT_TYPE_VALUE + type);
			if (Utility.isNotEmpty(jsonData)) {
				DictTypeVO dictTypeVO = JSON.parseObject(jsonData.toString(), DictTypeVO.class);
				list = dictTypeVO.getDictValueList();
				if (Utility.isEmpty(list)) {
					// 未获取值则跨服务调用
					list = getDictValueListByFeign(type);
				}
			} else {
				// 未获取值则跨服务调用
				list = getDictValueListByFeign(type);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Log.error(log, "\n 方法[{}]，异常：[{}]", "DictValueExportServiceImpl-getDictValueListByRedis", e.getMessage());
		}

		Log.debug(log, "\n 方法[{}]，出参：[{}]", "DictValueExportServiceImpl-getDictValueListByRedis",
				JSONObject.toJSONString(list));

		return list;
	}	
}
