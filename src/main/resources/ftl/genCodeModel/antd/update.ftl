import React, {useEffect, useState} from 'react';
import {message} from 'antd';
import ProForm, {ModalForm, ProFormDatePicker, ProFormDigit, ProFormText, } from '@ant-design/pro-form';
import 'antd/dist/antd.min.css';
import moment from "moment";

const Update${module} = (props: any) => {
  const {isModalVisible, isShowModal, actionRef, ${module?uncap_first}Id} = props;
  const [${module?uncap_first}, set${module}] = useState(undefined);// 将表单初始化的值设置成状态, 在编辑的时候, 使用这个状态
  const [formObj] = ProForm.useForm(); // 定义Form实例, 用来操作表单

  //修改时初始化数据
  const init${module} = async () => {
    const response = await load${module}({I_ID: ${module?uncap_first}Id});
    const ${module?uncap_first}Data = response.data;

    set${module}({...response.data});
    Object.keys(${module?uncap_first}Data);
    Object.values(${module?uncap_first}Data);
    Object.keys(${module?uncap_first}Data).forEach(key => formObj.setFieldsValue({[<#noparse>`${key}`</#noparse>]: ${module?uncap_first}Data[key]}));
  };

  //初始化
  useEffect(() => {
    if (${module?uncap_first}Id !== undefined) {
      init${module}();
    } else {
${antd_initForm!}
    }
  }, []);

  //form表单提交
  const handleSubmit = async (fields: any) => {
    const hide = message.loading('处理中...');
    let response = [];
      // 对提交后端数据处理
    const newFields = {};
    Object.assign(newFields, fields);
    if (${module?uncap_first} === undefined) {
      response = await insert${module}({...newFields});
    } else {
      response = await update${module}({${antd_primary}: (${module?uncap_first} as any).${antd_primary}, ...newFields});
    }
    hide();
    if (response.success) {
      message.success("操作成功");
    } else {
      return false;
    }
    return true;
  };

  return (
    <ModalForm
      form={formObj} //const [formObj] = ProForm.useForm(); // 定义Form实例, 用来操作表单
      title={(${module?uncap_first} !== undefined ? "修改" : "新增")} //加载数据给${module?uncap_first}赋值，判断是否是新增/修改
      width="800px"
      labelAlign={"right"} //文本框前面名称的位置
      visible={isModalVisible} //显示或隐藏
      onVisibleChange={isShowModal} //设置显示或隐藏
      onFinish={async (value) => { //表单提交 value表单中的值
        const success = await handleSubmit(value);
        if (success) {
          isShowModal(false);
          if (actionRef.current) {
            actionRef.current.reload();  //提交后刷新Protable
          }
        }
      }}
    >
${antd_updateForm}
    </ModalForm>
  );
};
export default Update${module};


