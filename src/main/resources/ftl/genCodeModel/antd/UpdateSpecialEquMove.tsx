import {useEffect, useState} from 'react';
import {message} from 'antd';
import ProForm, {ModalForm, ProFormDatePicker, ProFormText,} from '@ant-design/pro-form';
import 'antd/dist/antd.css';
import {load${module}, update${module}} from "././service";
import moment from "moment";
import ProFormSelect from "@ant-design/pro-form/lib/components/Select";

const Update${module} = (props: any) => {
  const {isModalVisible} = props; // 模态框是否显示
  const {isShowModal} = props; // 操作模态框显示隐藏的方法
  const {actionRef} = props; // 父组件传过来的表格的引用, 可以用来操作表格, 比如刷新表格
  const {${module?uncap_first}Id} = props; // 要编辑的ID, 添加的时候是undefined, 只有编辑才有
  const [${module?uncap_first}, set${module}] = useState(undefined);// 将表单初始化的值设置成状态, 在编辑的时候, 使用这个状态
  const [formObj] = ProForm.useForm(); // 定义Form实例, 用来操作表单
  const [orgName, setOrgName] = useState(undefined);
  const [orgNewName, setOrgNewName] = useState(undefined);
  const [deptName, setDeptName] = useState(undefined);
  const [deptNewName, setDeptNewName] = useState(undefined);
  const [eqpTypeName, setEqpTypeName] = useState(undefined);
  const [eqpName, setEqpName] = useState(undefined);
  const [deptCode, setDeptCode] = useState(undefined);

  const get${module} = async () => {
    const response = await load${module}({I_ID: ${module?uncap_first}Id});
    const ${module?uncap_first}Data = response.data[0];

    initOrg();
    onChangeDept(${module?uncap_first}Data.V_ORGCODE, ${module?uncap_first}Data.V_ORGNAME);
    onChangeEquType(${module?uncap_first}Data.V_DEPTCODE, ${module?uncap_first}Data.V_DEPTNAME);
    onChangeEqpCode(${module?uncap_first}Data.V_EQUTYPECODE, ${module?uncap_first}Data.V_EQUTYPENAME, ${module?uncap_first}Data.V_DEPTCODE);
    initOrgNew();
    onChangeDeptNew(${module?uncap_first}Data.V_NEWORGCODE, ${module?uncap_first}Data.V_NEWORGNAME);
    onChangeNewDept(${module?uncap_first}Data.V_NEWDEPTNAME);

    set${module}({...response.data[0]});
    Object.keys(${module?uncap_first}Data);
    Object.values(${module?uncap_first}Data);
    Object.keys(${module?uncap_first}Data).forEach(key => formObj.setFieldsValue({[`${key}`]: ${module?uncap_first}Data[key]}));

    formObj.setFieldsValue({['V_EQUCODE']: ${module?uncap_first}Data.V_EQUNAME}); //给form表单中name为V_EQUCODE的赋值
    setEqpName(${module?uncap_first}Data.V_EQUNAME);
  };

  //厂矿
  const [orgList, setOrgList] = useState([]);
  const initOrg = async () => {
    // 这里异步请求后台将数据拿到
    const deptList = await selectDept({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODE: '9908',
      V_V_DEPTCODENEXT: '%',
      V_V_DEPTTYPE: '基层单位'
    });
    const orgTempList: any = [];
    deptList.data.forEach(function (item: any) {
      const tempOrgDetail: any = {value: item.V_DEPTCODE, label: item.V_DEPTNAME}; //<ProFormSelect>组件下拉框的值需要label与value属性
      orgTempList.push(tempOrgDetail);
    });

    setOrgList(orgTempList);
  };

  //作业区
  const [deptList, setDeptList] = useState([]);
  const onChangeDept = async (id: any, name: any) => {
    const deptList = await selectDept({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODE: id,
      V_V_DEPTCODENEXT: '%',
      V_V_DEPTTYPE: '主体作业区'
    });
    const deptTempList: any = [];
    deptList.data.forEach(function (item: any) {
      const tempDeptDetail: any = {value: item.V_DEPTCODE, label: item.V_DEPTNAME};
      deptTempList.push(tempDeptDetail);
    });
    setDeptList(deptTempList);
    setOrgName(name);
  };

  //设备类型
  const [eqpTypeList, setEquTypeList] = useState([]);
  const onChangeEquType = async (id: any, name: any) => {
    setDeptCode(id);
    const eqpTypeList = await selectSeDeptEquType({V_V_PERSONCODE: 'qxzhangzf', V_V_DEPTCODENEXT: id});
    const eqpTypeTempList: any = [];
    eqpTypeList.data.forEach(function (item: any) {
      const tempEquTypeDetail: any = {value: item.V_EQUTYPECODE, label: item.V_EQUTYPENAME};
      eqpTypeTempList.push(tempEquTypeDetail);
    });
    setEquTypeList(eqpTypeTempList);
    setDeptName(name);
  };

  //设备名称
  const [eqpList, setEquList] = useState([]);
  const onChangeEqpCode = async (id: any, name: any, code: any) => {
    const eqpList = await selectSeDeptEqu({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODENEXT: deptCode === undefined ? code : deptCode,
      V_V_EQUTYPECODE: id,
    });
    const eqpTempList: any = [];
    eqpList.data.forEach(function (item: any) {
      const tempEquDetail: any = {value: item.V_EQUCODE, label: item.V_EQUNAME};
      eqpTempList.push(tempEquDetail);
    });
    setEquList(eqpTempList);
    setEqpTypeName(name);
  };

  const onChangeEqp = (label: any) => {
    setEqpName(label);
  };

  const onChangeNewDept = (label: any) => {
    setDeptNewName(label);
  };

  //接收单位
  const [orgNewList, setOrgNewList] = useState([]);
  const initOrgNew = async () => {
    // 这里异步请求后台将数据拿到
    const deptList = await selectDept({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODE: '9908',
      V_V_DEPTCODENEXT: '%',
      V_V_DEPTTYPE: '基层单位'
    });
    const orgTempList: any = [];
    deptList.data.forEach(function (item: any) {
      const tempOrgDetail: any = {value: item.V_DEPTCODE, label: item.V_DEPTNAME}; //<ProFormSelect>组件下拉框的值需要label与value属性
      orgTempList.push(tempOrgDetail);
    });
    setOrgNewList(orgTempList);
  };

  //接收作业区
  const [deptNewList, setDeptNewList] = useState([]);
  const onChangeDeptNew = async (id: any, name: any) => {
    const deptList = await selectDept({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODE: id,
      V_V_DEPTCODENEXT: '%',
      V_V_DEPTTYPE: '主体作业区'
    });
    const deptTempList: any = [];
    deptList.data.forEach(function (item: any) {
      const tempDeptDetail: any = {value: item.V_DEPTCODE, label: item.V_DEPTNAME};
      deptTempList.push(tempDeptDetail);
    });
    setDeptNewList(deptTempList);
    setOrgNewName(name);
  };


  //初始化
  useEffect(() => {
    if (${module?uncap_first}Id !== undefined) {
      get${module}();
    } else {
      initOrg();
      onChangeDept('9908', '齐大山选矿厂');
      initOrgNew();
      onChangeDeptNew('9908', '齐大山选矿厂');
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
      newFields['V_ORGNAME'] = orgName;
      newFields['V_DEPTNAME'] = deptName;
      newFields['V_EQUTYPENAME'] = eqpTypeName;
      newFields['V_EQUNAME'] = eqpName;
      newFields['V_NEWORGNAME'] = orgNewName;
      newFields['V_NEWDEPTNAME'] = deptNewName;
      newFields['V_PERSONCODE'] = 'qxzhangzf';
      if (${module?uncap_first} === undefined) {
        response = await update${module}({...newFields});
      } else {
        response = await update${module}({I_ID: (${module?uncap_first} as any).I_ID, ...newFields})
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
      {/*分组成一行*/}
      <ProForm.Group>
        <ProFormSelect
          options={orgList}
          width="sm"     //设置大小有xs，sm，md，lg
          name="V_ORGCODE"
          label="单位"
          rules={[   //设置必填项
            {
              required: true,
              message: '单位为必填项',
            },
          ]}
          fieldProps={{
            onSelect(value, options) {
              onChangeDept(options.value, options.label);
            }
          }}
          initialValue={'9908'}
        />
        <ProFormSelect
          options={deptList}
          width="sm"
          name="V_DEPTCODE"
          label="作业区"
          rules={[
            {
              required: true,
              message: '作业区为必填项',
            },
          ]}
          fieldProps={{
            onSelect(value, options) {
              onChangeEquType(options.value, options.label);
            }
          }}
          initialValue={'%'}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormSelect
          options={eqpTypeList}
          width="sm"
          name="V_EQUTYPECODE"
          label="设备类型"
          rules={[
            {
              required: true,
              message: '设备类型为必填项',
            },
          ]}
          fieldProps={{
            onSelect(value, options) {
              onChangeEqpCode(options.value, options.label, '');
            }
          }}
        />
        <ProFormSelect
          options={eqpList}
          width="sm"
          name="V_EQUCODE"
          label="设备名称"
          rules={[
            {
              required: true,
              message: '设备名称为必填项',
            },
          ]}
          fieldProps={{
            onChange(value, options) {
              // @ts-ignore
              onChangeEqp(options.label);
            }
          }}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormSelect
          options={orgNewList}
          width="sm"
          name="V_NEWORGCODE"
          label="接收单位"
          rules={[
            {
              required: true,
              message: '接收单位为必填项',
            },
          ]}
          fieldProps={{
            onSelect(value, options) {
              onChangeDeptNew(options.value, options.label);
            }
          }}
          initialValue={'9908'}
        />
        <ProFormSelect
          options={deptNewList}
          width="sm"
          name="V_NEWDEPTCODE"
          label="接收作业区"
          rules={[
            {
              required: true,
              message: '接收作业区为必填项',
            },
          ]}
          fieldProps={{
            onChange(value, options) {
              // @ts-ignore
              onChangeNewDept(options.label);
            }
          }}
          initialValue={'%'}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          label="新使用地点"
          width="sm"
          name="V_NEWADD"
        />
        <ProFormText
          label="新安装位置"
          width="sm"
          name="V_NEWSITE"
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormDatePicker
          width="sm"
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
      </ProForm.Group>
    </ModalForm>
  );
};
export default Update${module};


