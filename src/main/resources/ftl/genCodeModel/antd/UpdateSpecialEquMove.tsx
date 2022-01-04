import {useEffect, useState} from 'react';
import {message} from 'antd';
import ProForm, {ModalForm, ProFormDatePicker, ProFormDigit, ProFormText, } from '@ant-design/pro-form';
import 'antd/dist/antd.css';
import {load${module}, update${module}, insert${module}} from "././service";
import moment from "moment";

const Update${module} = (props: any) => {
  const {isModalVisible} = props; // 模态框是否显示
  const {isShowModal} = props; // 操作模态框显示隐藏的方法
  const {actionRef} = props; // 父组件传过来的表格的引用, 可以用来操作表格, 比如刷新表格
  const {${module?uncap_first}Id} = props; // 要编辑的ID, 添加的时候是undefined, 只有编辑才有
  const [${module?uncap_first}, set${module}] = useState(undefined);// 将表单初始化的值设置成状态, 在编辑的时候, 使用这个状态
  const [formObj] = ProForm.useForm(); // 定义Form实例, 用来操作表单

  //修改时初始化数据
  const init${module} = async () => {
    const response = await load${module}({I_ID: ${module?uncap_first}Id});
    const ${module?uncap_first}Data = response.data[0];

    set${module}({...response.data[0]});
    Object.keys(${module?uncap_first}Data);
    Object.values(${module?uncap_first}Data);
    Object.keys(${module?uncap_first}Data).forEach(key => formObj.setFieldsValue({[<#noparse>`${key}`</#noparse>]: ${module?uncap_first}Data[key]}));
  };

  //初始化
  useEffect(() => {
    if (${module?uncap_first}Id !== undefined) {
      init${module}();
    } else {

    }
  }, []);

  //form表单提交
  const handleSubmit = async (fields: any) => {
    const hide = message.loading('处理中...');
    let response = [];
    try {
      // 对提交后端日期格式处理
      const newFields = {};
      Object.assign(newFields, fields);
      if (${module?uncap_first} === undefined) {
        response = await insert${module}({...newFields});
      } else {
        response = await update${module}({${antd_primary}: (${module?uncap_first} as any).${antd_primary}, ...newFields})
      }
      hide();
      if (response.success) {
        message.success("操作成功");
      } else {
        message.error('操作失败');
      }
      return true;
    } catch (e) {
      hide();
      message.error('操作失败');
      return false;
    }
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
      <ProForm.Group>
        <ProFormText
          label="新使用地点"
          width="lg"
          name="V_NEWADD"
        />
        <ProFormText
          label="新安装位置"
          width="lg"
          name="V_NEWSITE"
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormDatePicker
          width="lg"
          name="V_MOVETIME"
          label="调拨时间"
          rules={[
            {
              required: true,
              message: '调拨时间为必填项',
            },
          ]}
          initialValue={moment()}  //当前时间
        />
        <ProFormDigit
            width="lg"
            name="V_I_WORK_ACTIVITY"
            label="调拨时间"
            min={0}
            //max={99999}
            fieldProps={{ precision: 2 }}// 小数位数
            rules={[
              {
                required: true
              }
            ]}
        />
      </ProForm.Group>
    </ModalForm>
  );
};
export default Update${module};


