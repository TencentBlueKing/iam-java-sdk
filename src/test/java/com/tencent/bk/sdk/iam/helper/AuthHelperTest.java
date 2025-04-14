/*
 * TencentBlueKing is pleased to support the open source community by making
 * 蓝鲸智云-权限中心Java SDK(iam-java-sdk) available.
 * Copyright (C) 2017-2021 THL A29 Limited, a Tencent company. All rights reserved.
 * Licensed under the MIT License (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://opensource.org/licenses/MIT
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.bk.sdk.iam.helper;


import com.tencent.bk.sdk.iam.constants.ExpressionOperationEnum;
import com.tencent.bk.sdk.iam.dto.InstanceDTO;
import com.tencent.bk.sdk.iam.dto.PathInfoDTO;
import com.tencent.bk.sdk.iam.dto.expression.ExpressionDTO;
import com.tencent.bk.sdk.iam.util.JsonUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthHelperTest {

	@Test
	public void calculateExpression() {
		Map<String, InstanceDTO> instanceMap = new HashMap<>(2);
		ExpressionDTO ExpressionDTO = new ExpressionDTO();
		InstanceDTO instanceDTO1 = new InstanceDTO();
		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("v3test");
		instanceDTO1.setType("pipeline");
		instanceDTO1.setId("p-0d1fff4dabca4fc282e5ff63644bd339");
		instanceDTO1.setPath(pathInfoDTO);
		System.out.println(instanceDTO1);
		instanceMap.put(instanceDTO1.getType(), instanceDTO1);

		InstanceDTO instanceDTO2 = new InstanceDTO();
		PathInfoDTO pipelineGroupPath = new PathInfoDTO();
		pipelineGroupPath.setType("pipeline_group");
		pipelineGroupPath.setId("mjrjvoem");
		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("rbactest");
		pathInfoDTO2.setChild(pipelineGroupPath);
		instanceDTO2.setType("pipeline");
		instanceDTO2.setId("p-dfdfdff4dabca4fc282e5ff63644bd339");
		instanceDTO2.setPaths(Arrays.asList(pathInfoDTO, pathInfoDTO2));
		System.out.println(instanceDTO2);


		List<ExpressionDTO> content = new ArrayList<>();
		ExpressionDTO content1 = new ExpressionDTO();
		content1.setOperator(ExpressionOperationEnum.START_WITH);
		content1.setField("pipeline._bk_iam_path_");
		content1.setValue("/project,demo/pipeline,p-098b68a251ae4ec4b6f4fde87767387f/");

		ExpressionDTO content2 = new ExpressionDTO();
		content2.setOperator(ExpressionOperationEnum.START_WITH);
		content2.setField("pipeline._bk_iam_path_");
		content2.setValue("/project,demo/pipeline,p-12b2c343109f43a58a79dcb9e3721c1b/");

		ExpressionDTO content3 = new ExpressionDTO();
		content3.setOperator(ExpressionOperationEnum.START_WITH);
		content3.setField("pipeline._bk_iam_path_");
		content3.setValue("/project,v3test/pipeline,p-0d1fff4dabca4fc282e5ff63644bd339/");

		ExpressionDTO content4 = new ExpressionDTO();
		content4.setOperator(ExpressionOperationEnum.START_WITH);
		content4.setField("pipeline._bk_iam_path_");
		content4.setValue("/project,v3test/pipeline,p-54fb8b6562584df4b3693f7c787c105a/");

		ExpressionDTO content5 = new ExpressionDTO();
		content5.setOperator(ExpressionOperationEnum.STRING_CONTAINS);
		content5.setField("pipeline._bk_iam_path_");
		content5.setValue("/pipeline_group,mjrjvoem/");

		content.add(content1);
		content.add(content2);
		content.add(content3);
		content.add(content4);
		content.add(content5);

		ExpressionDTO.setField(null);
		ExpressionDTO.setOperator(ExpressionOperationEnum.OR);
		ExpressionDTO.setContent(content);

		Boolean isallow = AuthHelper.calculateExpression(instanceMap, ExpressionDTO);
		System.out.println(isallow);

		instanceMap.put(instanceDTO2.getType(), instanceDTO2);
		isallow = AuthHelper.calculateExpression(instanceMap, ExpressionDTO);
		System.out.println(isallow);
	}

	/** ExpressionDTO(operator=AND, field=null, value=null, content=[
	 * 		ExpressionDTO(operator=IN, field=pipeline.id, value=[p-397e11330be143378bd39eb26358dcf8, p-cbb155c4b0b245e2adba872b73873558], content=null),
	 * 		ExpressionDTO(operator=START_WITH, field=pipeline._bk_iam_path_, value=/project,froms/, content=null)]
	 * 		)
	 *
	 */
	@Test
	public void calculateExpression1() {
		ExpressionDTO expressionDTO = new ExpressionDTO();
		expressionDTO.setOperator(ExpressionOperationEnum.AND);

		ExpressionDTO childExpression1 = new ExpressionDTO();
		ExpressionDTO childExpression2 = new ExpressionDTO();
		childExpression1.setOperator(ExpressionOperationEnum.IN);
		childExpression1.setField("pipeline.id");
		List<String> pipelineId = new ArrayList<>();
		pipelineId.add("p-397e11330be143378bd39eb26358dcf8");
		pipelineId.add("p-cbb155c4b0b245e2adba872b73873558");
		childExpression1.setValue(pipelineId);

		childExpression2.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression2.setField("pipeline._bk_iam_path_");
		childExpression2.setValue("/project,froms/");

		List<ExpressionDTO> content = new ArrayList<>();
		content.add(childExpression1);
		content.add(childExpression2);
		expressionDTO.setContent(content);

//		InstanceDTO instanceDTO = new InstanceDTO();
//		instanceDTO.setId("p-cbb155c4b0b245e2adba872b73873558");
//		instanceDTO.setSystem("bk_ci");
//		instanceDTO.setType("project");
//		PathInfoDTO pathInfoDTO = new PathInfoDTO();
//		pathInfoDTO.setType("project");
//		pathInfoDTO.setId("froms");
//		instanceDTO.setPath(pathInfoDTO);
//		Map<String, InstanceDTO> instanceMap1 = new HashMap<>(1);
//		instanceMap1.put(instanceDTO.getType(), instanceDTO);
//		Boolean isallow = AuthHelper.calculateExpression(instanceMap1, expressionDTO);
//
		Map<String, InstanceDTO> instanceMap2 = new HashMap<>(1);

		InstanceDTO instanceDTO1 = new InstanceDTO();
		instanceDTO1.setId("p-cbb155c4b0b245e2adba872b73873558");
		instanceDTO1.setSystem("bk_ci");
		instanceDTO1.setType("pipeline");
		PathInfoDTO pathInfoDTO1 = new PathInfoDTO();
		pathInfoDTO1.setType("project");
		pathInfoDTO1.setId("froms");
		instanceDTO1.setPath(pathInfoDTO1);
		instanceMap2.put(instanceDTO1.getType(), instanceDTO1);
		System.out.println("instance:" + instanceMap2);
		System.out.println("exp:" + expressionDTO);
		Boolean isallow1 = AuthHelper.calculateExpression(instanceMap2, expressionDTO);
		System.out.println(isallow1);
	}

	@Test
	public void calculateInstanceList1() {
		ExpressionDTO expressionDTO = new ExpressionDTO();
		expressionDTO.setField("project.id");
		expressionDTO.setOperator(ExpressionOperationEnum.IN);
		expressionDTO.setValue(Arrays.asList("demo", "test1"));
		Assertions.assertEquals(
				Arrays.asList("demo", "test1"),
				AuthHelper.calculateInstanceList(expressionDTO, "project", null)
		);

		ExpressionDTO expressionDTO1 = new ExpressionDTO();
		expressionDTO1.setField("project.id");
		expressionDTO1.setOperator(ExpressionOperationEnum.EQUAL);
		expressionDTO1.setValue("demo");
		Assertions.assertEquals(
				Collections.singletonList("demo"),
				AuthHelper.calculateInstanceList(expressionDTO1, "project",null)
		);

		ExpressionDTO expressionDTO2 = new ExpressionDTO();
		expressionDTO2.setField("project.id");
		expressionDTO2.setOperator(ExpressionOperationEnum.ANY);
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expressionDTO2,"project", null)
		);

		ExpressionDTO expressionDTO3 = new ExpressionDTO();
		expressionDTO3.setOperator(ExpressionOperationEnum.OR);
		List<ExpressionDTO> content = new ArrayList<>(2);
		expressionDTO3.setContent(content);

		ExpressionDTO expressionDTO32 = new ExpressionDTO();
		expressionDTO32.setOperator(ExpressionOperationEnum.IN);
		expressionDTO32.setField("project.id");
		expressionDTO32.setValue(Arrays.asList("demo", "test1"));
		content.add(expressionDTO32);

		ExpressionDTO expressionDTO31 = new ExpressionDTO();
		expressionDTO31.setOperator(ExpressionOperationEnum.ANY);
		expressionDTO31.setField("project.id");
		expressionDTO31.setValue(Collections.emptyList());
		content.add(expressionDTO31);

		Assertions.assertEquals(
				Arrays.asList("*", "demo", "test1"),
				AuthHelper.calculateInstanceList(expressionDTO3,"project", null)
		);
	}

	@Test
	public void getInstanceList1() {
		List<ExpressionDTO> expressionList = buildExpression();
		PathInfoDTO pathInfoDTO1 = new PathInfoDTO();
		pathInfoDTO1.setType("project");
		pathInfoDTO1.setId("test1");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expressionList.get(0), "pipeline", pathInfoDTO1)
		);

		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("demo");
		Assertions.assertEquals(
				Collections.emptyList(),
				AuthHelper.calculateInstanceList(expressionList.get(0), "pipeline", pathInfoDTO2)
		);
	}

	@Test
	public void getInstanceList2() {
		List<ExpressionDTO> expressionList = buildExpression();
		PathInfoDTO pathInfoDTO1 = new PathInfoDTO();
		pathInfoDTO1.setType("project");
		pathInfoDTO1.setId("demo");
		Assertions.assertEquals(
				Arrays.asList(
						"p-098b68a251ae4ec4b6f4fde87767387f",
						"p-12b2c343109f43a58a79dcb9e3721c1b",
						"p-54a8619d1f754d32b5b2bc249a74f26c"
				),
				AuthHelper.calculateInstanceList(expressionList.get(1), "pipeline", pathInfoDTO1)
		);

		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("test1");
		Assertions.assertEquals(
				Collections.emptyList(),
				AuthHelper.calculateInstanceList(expressionList.get(1), "pipeline", pathInfoDTO2)
		);
	}

	@Test
	public void getInstanceList3() {
		List<ExpressionDTO> expressionList = buildExpression();
		PathInfoDTO pathInfoDTO1 = new PathInfoDTO();
		pathInfoDTO1.setType("project");
		pathInfoDTO1.setId("demo");
		Assertions.assertEquals(
				Arrays.asList(
						"p-098b68a251ae4ec4b6f4fde87767387f",
						"p-12b2c343109f43a58a79dcb9e3721c1b",
						"p-54a8619d1f754d32b5b2bc249a74f26c"
				),
				AuthHelper.calculateInstanceList(expressionList.get(2), "pipeline", pathInfoDTO1)
		);

		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("v3test");
		Assertions.assertEquals(
				Arrays.asList(
						"p-0d1fff4dabca4fc282e5ff63644bd339",
						"p-54fb8b6562584df4b3693f7c787c105a"
				),
				AuthHelper.calculateInstanceList(expressionList.get(2), "pipeline", pathInfoDTO2)
		);
	}

	@Test
	public void getInstanceList4() {
		List<ExpressionDTO> expressionList = buildExpression();
		PathInfoDTO pathInfoDTO1 = new PathInfoDTO();
		pathInfoDTO1.setType("project");
		pathInfoDTO1.setId("demo");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expressionList.get(3), "pipeline", pathInfoDTO1)
		);

		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("v3test");
		Assertions.assertEquals(
				Arrays.asList(
						"p-0d1fff4dabca4fc282e5ff63644bd339",
						"p-54fb8b6562584df4b3693f7c787c105a"
				),
				AuthHelper.calculateInstanceList(expressionList.get(3), "pipeline", pathInfoDTO2)
		);
	}

	@Test
	public void getInstanceList5() {
		List<ExpressionDTO> expressionList = buildExpression();
		Assertions.assertEquals(
				Collections.singletonList("test_3"),
				AuthHelper.calculateInstanceList(expressionList.get(4), "credential", null)
		);

		PathInfoDTO pathInfoDTO2 = new PathInfoDTO();
		pathInfoDTO2.setType("project");
		pathInfoDTO2.setId("v3test");
		Assertions.assertEquals(
				Arrays.asList(
						"test_3",
						"test"
				),
				AuthHelper.calculateInstanceList(expressionList.get(4), "credential", pathInfoDTO2)
		);
	}

	@Test
	public void getResourceInstanceTest6() {
		ExpressionDTO expression1 = new ExpressionDTO();
		expression1.setOperator(ExpressionOperationEnum.OR);

		ExpressionDTO childExpression1 = new ExpressionDTO();
		childExpression1.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression1.setField("credential._bk_iam_path_");
		childExpression1.setValue("/project,fitztest/,");

		ExpressionDTO childExpression2 = new ExpressionDTO();
		childExpression2.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression2.setField("credential._bk_iam_path_");
		childExpression2.setValue("/project,testaaa/,");

		List<ExpressionDTO> expression1Content = new ArrayList<>(2);
		expression1Content.add(childExpression1);
		expression1Content.add(childExpression2);
		expression1.setContent(expression1Content);

		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("fitztest");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expression1, "credential", pathInfoDTO)
		);
	}

	@Test
	public void getResourceInstanceTest7() {
		ExpressionDTO e1 = new ExpressionDTO();
		e1.setOperator(ExpressionOperationEnum.OR);

		ExpressionDTO e21 = new ExpressionDTO();
		e21.setOperator(ExpressionOperationEnum.START_WITH);
		e21.setField( "credential._bk_iam_path_");
		e21.setValue("/project,testaaa/,");

		ExpressionDTO e22 = new ExpressionDTO();
		e22.setOperator(ExpressionOperationEnum.OR);

		ExpressionDTO e31 = new ExpressionDTO();
		e31.setOperator(ExpressionOperationEnum.AND);

		ExpressionDTO e32 = new ExpressionDTO();
		e32.setOperator(ExpressionOperationEnum.AND);

		ExpressionDTO e41 = new ExpressionDTO();
		e41.setOperator(ExpressionOperationEnum.IN);
		e41.setField("credential.id");
		e41.setValue(Arrays.asList("fabio", "dsahs"));

		ExpressionDTO e42 = new ExpressionDTO();
		e42.setOperator(ExpressionOperationEnum.START_WITH);
		e42.setField("credential._bk_iam_path_");
		e42.setValue("/project,testaaa/");

		ExpressionDTO e43 = new ExpressionDTO();
		e43.setOperator(ExpressionOperationEnum.IN);
		e43.setField("credential.id");
		e43.setValue(Arrays.asList("001", "002", "003"));

		ExpressionDTO e44 = new ExpressionDTO();
		e44.setOperator(ExpressionOperationEnum.START_WITH);
		e44.setField("credential._bk_iam_path_");
		e44.setValue("/project,aa20200908");

		List<ExpressionDTO> e31content = new ArrayList<>();
		e31content.add(e41);
		e31content.add(e42);
		e31.setContent(e31content);
		List<ExpressionDTO> e32Content = new ArrayList<>();
		e32Content.add(e43);
		e32Content.add(e44);
		e32.setContent(e32Content);
		List<ExpressionDTO> e22content = new ArrayList<>();
		e22content.add(e31);
		e22content.add(e32);
		e22.setContent(e22content);
		List<ExpressionDTO> e1Content = new ArrayList<>();
		e1Content.add(e21);
		e1Content.add(e22);
		e1.setContent(e1Content);

		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("aa20200908");
		Assertions.assertEquals(
				Arrays.asList("001", "002", "003"),
				AuthHelper.calculateInstanceList(e1, "credential", pathInfoDTO)
		);
	}

	@Test
	public void getResourceInstanceTest9() {
		ExpressionDTO expression1 = new ExpressionDTO();
		expression1.setOperator(ExpressionOperationEnum.OR);

		ExpressionDTO childExpression1 = new ExpressionDTO();
		childExpression1.setOperator(ExpressionOperationEnum.ANY);
		childExpression1.setField("credential.id");

		ExpressionDTO childExpression2 = new ExpressionDTO();
		childExpression2.setOperator(ExpressionOperationEnum.AND);

		ExpressionDTO childExpression2Child1 = new ExpressionDTO();
		childExpression2Child1.setOperator(ExpressionOperationEnum.EQUAL);
		childExpression2Child1.setField("credential.id");
		childExpression2Child1.setValue("jvtest");

		ExpressionDTO childExpression2Child2 = new ExpressionDTO();
		childExpression2Child2.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression2Child2.setField("credential._bk_iam_path_");
		childExpression2Child2.setValue("/project,jttest/");

		List<ExpressionDTO> expressionChild2Content = new ArrayList<>(2);
		expressionChild2Content.add(childExpression2Child1);
		expressionChild2Content.add(childExpression2Child2);
		childExpression2.setContent(expressionChild2Content);

		List<ExpressionDTO> expression1Content = new ArrayList<>(2);
		expression1Content.add(childExpression1);
		expression1Content.add(childExpression2);
		expression1.setContent(expression1Content);

		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("jttest");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expression1, "credential", pathInfoDTO)
		);
	}

	@Test
	public void getResourceInstanceTest10() {
		ExpressionDTO expression = new ExpressionDTO();
		expression.setOperator(ExpressionOperationEnum.EQUAL);
		expression.setValue("testProject");
		expression.setField("project.id");

		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("testProject");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expression, "pipeline", pathInfoDTO)
		);
	}

	@Test
	public void getResourceInstanceTest11() {
		List<ExpressionDTO> expressionList = buildExpression2();

		String resourceType = "pipeline";
		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("bkdevops");
		List<String> resultSet1 = Arrays.asList("*", "873416", "873400");
		List<String> resultSet2 = Arrays.asList("868835", "873416", "873400");
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expressionList.get(0), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				Collections.singletonList("*"),
				AuthHelper.calculateInstanceList(expressionList.get(1), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(2), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(3), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(4), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet2,
				AuthHelper.calculateInstanceList(expressionList.get(5), resourceType, pathInfoDTO)
		);
	}

	@Test
	public void getResourceInstanceTest12() {
		List<ExpressionDTO> expressionList = buildExpression2();

		String resourceType = "pipeline";
		PathInfoDTO pathInfoDTO = new PathInfoDTO();
		pathInfoDTO.setType("project");
		pathInfoDTO.setId("iamV3test-080303");
		List<String> resultSet1 = Collections.singletonList("*");
		List<String> resultSet2 = Arrays.asList("868835", "*");
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(0), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(1), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet2,
				AuthHelper.calculateInstanceList(expressionList.get(2), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet2,
				AuthHelper.calculateInstanceList(expressionList.get(3), resourceType, pathInfoDTO)
		);
		Assertions.assertEquals(
				resultSet1,
				AuthHelper.calculateInstanceList(expressionList.get(4), resourceType, pathInfoDTO)
		);
	}

	private List<ExpressionDTO> buildExpression() {
		List<ExpressionDTO> expressionList = new ArrayList<>(5);
		// 单项目下任务 {"field":"pipeline._bk_iam_path_","op":"starts_with","value":"/project,test1/"}
		ExpressionDTO expressionDTO1 = new ExpressionDTO();
		expressionDTO1.setField("pipeline._bk_iam_path_");
		expressionDTO1.setOperator(ExpressionOperationEnum.START_WITH);
		expressionDTO1.setValue("/project,test1/");
		expressionList.add(expressionDTO1);

		// and一层嵌套
		ExpressionDTO expressionDTO2 = new ExpressionDTO();
		ExpressionDTO childExpression1 = new ExpressionDTO();
		childExpression1.setField("pipeline.id");
		childExpression1.setOperator(ExpressionOperationEnum.IN);
		List<String> pipelineList1 = new ArrayList<>();
		pipelineList1.add("p-098b68a251ae4ec4b6f4fde87767387f");
		pipelineList1.add("p-12b2c343109f43a58a79dcb9e3721c1b");
		pipelineList1.add("p-54a8619d1f754d32b5b2bc249a74f26c");
		childExpression1.setValue(pipelineList1);

		ExpressionDTO childExpression2 = new ExpressionDTO();
		childExpression2.setField("pipeline._bk_iam_path_");
		childExpression2.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression2.setValue("/project,demo/");

		expressionDTO2.setContent(new ArrayList<>());
		expressionDTO2.getContent().add(childExpression1);
		expressionDTO2.getContent().add(childExpression2);
		expressionDTO2.setOperator(ExpressionOperationEnum.AND);
		expressionList.add(expressionDTO2);

		// and 二级嵌套
		ExpressionDTO expressionDTO3 = new ExpressionDTO();

		ExpressionDTO lastExpression1 = new ExpressionDTO();
		lastExpression1.setField("pipeline.id");
		lastExpression1.setOperator(ExpressionOperationEnum.IN);
		List<String> pipelineList2 = new ArrayList<>(2);
		pipelineList2.add("p-0d1fff4dabca4fc282e5ff63644bd339");
		pipelineList2.add("p-54fb8b6562584df4b3693f7c787c105a");
		lastExpression1.setValue(pipelineList2);

		ExpressionDTO lastExpression2 = new ExpressionDTO();
		lastExpression2.setField("pipeline._bk_iam_path_");
		lastExpression2.setOperator(ExpressionOperationEnum.START_WITH);
		lastExpression2.setValue("/project,v3test/");

		ExpressionDTO childExpression3 = new ExpressionDTO();
		childExpression3.setContent(new ArrayList<>());
		childExpression3.getContent().add(lastExpression1);
		childExpression3.getContent().add(lastExpression2);
		childExpression3.setOperator(ExpressionOperationEnum.AND);

		expressionDTO3.setContent(new ArrayList<>(2));
		expressionDTO3.getContent().add(childExpression3);
		expressionDTO3.getContent().add(expressionDTO2);
		expressionDTO3.setOperator(ExpressionOperationEnum.OR);
		expressionList.add(expressionDTO3);

		ExpressionDTO expressionDTO4 = new ExpressionDTO();
		expressionDTO4.setContent(new ArrayList<>());
		expressionDTO4.setOperator(ExpressionOperationEnum.OR);
		expressionDTO4.getContent().add(childExpression3);

		ExpressionDTO childExpression4 = new ExpressionDTO();
		childExpression4.setContent(new ArrayList<>());
		childExpression4.setField("pipeline._bk_iam_path_");
		childExpression4.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression4.setValue("/project,demo/");
		expressionDTO4.getContent().add(childExpression4);
		expressionList.add(expressionDTO4);

		ExpressionDTO expressionDTO5 = new ExpressionDTO();
		expressionDTO5.setContent(new ArrayList<>());
		expressionDTO5.setOperator(ExpressionOperationEnum.OR);
		ExpressionDTO childExpression5 = new ExpressionDTO();
		childExpression5.setField("credential.id");
		childExpression5.setOperator(ExpressionOperationEnum.EQUAL);
		childExpression5.setValue("test_3");

		ExpressionDTO childExpression6 = new ExpressionDTO();
		ExpressionDTO childExpression7 = new ExpressionDTO();
		ExpressionDTO childExpression8 = new ExpressionDTO();
		childExpression5.setContent(new ArrayList<>());
		childExpression6.setContent(new ArrayList<>());
		childExpression7.setOperator(ExpressionOperationEnum.EQUAL);
		childExpression7.setValue("test");
		childExpression7.setField("credential.id");

		childExpression8.setOperator(ExpressionOperationEnum.START_WITH);
		childExpression8.setValue("/project,v3test/");
		childExpression8.setField("credential._bk_iam_path_");

		childExpression6.getContent().add(childExpression7);
		childExpression6.getContent().add(childExpression8);
		childExpression6.setOperator(ExpressionOperationEnum.AND);
		expressionDTO5.getContent().add(childExpression5);
		expressionDTO5.getContent().add(childExpression6);
		expressionList.add(expressionDTO5);
		return expressionList;
	}

	private List<ExpressionDTO> buildExpression2() {
		String expressionStr1 = "{\"content\":[{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"AND\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"}],\"op\":\"OR\"}";
		String expressionStr2 = "{\"content\":[{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"AND\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"}],\"op\":\"OR\"}";
		String expressionStr3 = "{\"content\":[{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"AND\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"OR\"}";
		String expressionStr4 = "{\"content\":[{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"AND\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"OR\"}";
		String expressionStr5 = "{\"content\":[{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,iamV3test-080303/\"}],\"op\":\"AND\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"OR\"}";
		String expressionStr6 = "{\"content\":[{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"868835\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"},{\"content\":[{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"873400\",\"873416\"]},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"starts_with\",\"value\":\"/project,bkdevops/\"}],\"op\":\"AND\"}],\"op\":\"OR\"}";

		List<ExpressionDTO> expressionList = new ArrayList<>(6);
		try {
			expressionList.add(JsonUtil.fromJson(expressionStr1, ExpressionDTO.class));
			expressionList.add(JsonUtil.fromJson(expressionStr2, ExpressionDTO.class));
			expressionList.add(JsonUtil.fromJson(expressionStr3, ExpressionDTO.class));
			expressionList.add(JsonUtil.fromJson(expressionStr4, ExpressionDTO.class));
			expressionList.add(JsonUtil.fromJson(expressionStr5, ExpressionDTO.class));
			expressionList.add(JsonUtil.fromJson(expressionStr6, ExpressionDTO.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return expressionList;
	}


	@Test
	public void testGroupRbacInstanceByAttribute() throws IOException {
		String expressionStr = "{\"content\":[{\"content\":[{\"content\":[{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"string_contains\",\"value\":\"/project,greysonfang-rbac-test-113/\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"string_contains\",\"value\":\"/project,asdasdasd/\"}],\"op\":\"OR\"},{\"content\":[{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"string_contains\",\"value\":\"/pipeline_group,proxgyem/\"},{\"field\":\"pipeline._bk_iam_path_\",\"op\":\"string_contains\",\"value\":\"/pipeline_group,mjrjvoem/\"}],\"op\":\"OR\"},{\"field\":\"pipeline.id\",\"op\":\"in\",\"value\":[\"p-cb56802bf8eb4b6ebc2b44c77ed545bf\",\"p-a7677e1e77a5414199ff294f5a35162a\",\"p-fee8bc4ad534421e80fefb48d1310bb8\",\"p-4acf760c304d4b99851b188260ff5e45\"]}],\"op\":\"OR\"},{\"field\":\"pipeline.id\",\"op\":\"eq\",\"value\":\"p-42f8638d709a4fc9b6e9292f1c232456\"}],\"op\":\"OR\"}";
		ExpressionDTO expression = JsonUtil.fromJson(expressionStr, ExpressionDTO.class);

		PathInfoDTO projectPath = new PathInfoDTO();
		projectPath.setType("project");
		projectPath.setId("greysonfang-rbac-test-113");
		Map<String, List<String>> instanceMap = AuthHelper.groupRbacInstanceByType(expression);
		System.out.println(instanceMap);
	}
}
