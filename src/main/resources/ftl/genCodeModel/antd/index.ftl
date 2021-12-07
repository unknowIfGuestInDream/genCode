import {PlusOutlined} from '@ant-design/icons';
import {Button, message} from 'antd';
import {useEffect, useRef, useState} from 'react';
// @ts-ignore
import {connect} from 'umi';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {deleteEquMove, selectDept, selectEquMove, selectSeDeptEqu, selectSeDeptEquType} from './service.ftl';
import 'moment/locale/zh-cn'
import moment from "moment";
import {DownloadOutlined} from "@ant-design/icons/lib";

/* React.FC<>的在typescript使用的一个泛型，FC就是FunctionComponent的缩写，是函数组件，在这个泛型里面可以使用useState */
export const Applications = () => {
  //     取值            赋值                          定义类型
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [equMoveId, setEquMoveId] = useState(undefined);
  const [deptCode, setDeptCode] = useState(undefined);
  const [eqpTypeCode, setEqpTypeCode] = useState(undefined);
  const [eqpCode, setEqpCode] = useState(undefined);
  const [beginDate, setBeginDate] = useState(undefined);
  const [endDate, setEndDate] = useState(undefined);
  const [orgCode, setOrgCode] = useState(undefined);
  const actionRef = useRef<ActionType>();

  /**
   * 控制模态框显示和隐藏
   */
  //                       boolean 类型                                   当前参数可以为空
  const isShowModal = (show: boolean | ((prevState: boolean) => boolean), id = undefined) => {
    setEquMoveId(id);
    setIsModalVisible(show);
  };

  //删除 async 与 await联用
  const handleRemove = async (I_PLANID: any) => {
    const hide = message.loading('正在删除');
    if (!I_PLANID) return true;
    try {
		
	  // 调用service中的请求
      await deleteEquMove({I_ID: I_PLANID});
      hide();
      message.success('删除成功，即将刷新');
	  //刷新Protable
      actionRef.current?.reloadAndRest?.();
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试');
      return false;
    }
  };


  //厂矿
  const [orgList, setOrgList] = useState([]);
  const initOrg = async () => {
    // 这里异步请求后台获取数据 {}中是参数
    const deptList = await selectDept({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODE: '9908',
      V_V_DEPTCODENEXT: '%',
      V_V_DEPTTYPE: '基层单位'
    });
    const orgTempList: any = [];
	//遍历请求返回值 item相当于list<Map<String, Object>> 中的map
	// function (参数){}  等同于 (参数) => {} 
    deptList.data.forEach(function (item: any) {
      const tempOrgDetail: any = {value: item.V_DEPTCODE, label: item.V_DEPTNAME}; //<ProFormSelect>组件下拉框的值需要label与value属性
      orgTempList.push(tempOrgDetail);
    });

	//给 const [orgList, setOrgList] = useState([]); 赋值
    setOrgList(orgTempList);
  };


  //作业区
  const [deptList, setDeptList] = useState([]);
  const onChangeDept = async (id: any) => {
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
  };

  //设备类型
  const [eqpTypeList, setEquTypeList] = useState([]);
  const onChangeEquType = async (id: any) => {
    setDeptCode(id);
    const eqpTypeList = await selectSeDeptEquType({V_V_PERSONCODE: 'qxzhangzf', V_V_DEPTCODENEXT: id});
    const eqpTypeTempList: any = [];
    eqpTypeList.data.forEach(function (item: any) {
      const tempEquTypeDetail: any = {value: item.V_EQUTYPECODE, label: item.V_EQUTYPENAME};
      eqpTypeTempList.push(tempEquTypeDetail);
    });
    setEquTypeList(eqpTypeTempList);
  };

  //设备名称
  const [eqpList, setEquList] = useState([]);
  const onChangeEqpCode = async (id: any) => {
    setEqpTypeCode(id);
    const eqpList = await selectSeDeptEqu({
      V_V_PERSONCODE: 'qxzhangzf',
      V_V_DEPTCODENEXT: deptCode,
      V_V_EQUTYPECODE: id,
    });
    const eqpTempList: any = [];
    eqpList.data.forEach(function (item: any) {
      const tempEquDetail: any = {value: item.V_EQUCODE, label: item.V_EQUNAME};
      eqpTempList.push(tempEquDetail);
    });
    setEquList(eqpTempList);
  };

  const onChangeEqp = (label: any) => {
    setEqpCode(label);
  };

  //useEffect仅初始化执行一次,[] 空数组 执行一次
  useEffect(() => {
    initOrg();
    onChangeDept('9908');
  }, []);


  //定义 Protable的列 columns放在Protable
  const columns: ProColumns[] = [
    {
      title: '序号', //列名
      dataIndex: 'RN', //返回的字段
      width: 60, //宽度
      hideInSearch: true // 在Search中隐藏，不做查询条件
    },
    {
      title: '设备类型',
      dataIndex: 'V_EQUNCODE',
      width: 200,
      hideInSearch: true
    },
    {
      title: '设备名称',
      dataIndex: 'V_EQUNAME',
      width: 200,
      hideInSearch: true
    },
    {
      title: '原单位',
      dataIndex: 'V_ORGNAME',
      width: 150,
      hideInSearch: true
    },
    {
      title: '原作业区',
      dataIndex: 'V_DEPTNAME',
      width: 150,
      hideInSearch: true
    },
    {
      title: '原使用地点',
      dataIndex: 'V_SITE',
      width: 200,
      hideInSearch: true
    },
    {
      title: '接收单位',
      dataIndex: 'V_NEWORGNAME',
      width: 150,
      hideInSearch: true
    },
    {
      title: '接收作业区',
      dataIndex: 'V_NEWDEPTNAME',
      width: 150,
      hideInSearch: true
    },
    {
      title: '新使用地点',
      dataIndex: 'V_NEWADD',
      width: 200,
      hideInSearch: true
    },
    {
      title: '新安装位置',
      dataIndex: 'V_NEWSITE',
      width: 200,
      hideInSearch: true
    },
    {
      title: '状态',
      dataIndex: 'V_STATUS',
      width: 100,
      hideInSearch: true
    },
    {
      title: '开始时间',
      dataIndex: 'V_BDATE',
      valueType: 'date', //定义时间类型，用于Search中
      hideInTable: true, //在Protable中隐藏，不显示
      initialValue: moment(moment().year() + '-01-01').format('YYYY-MM-DD') //设置默认值
    },
    {
      title: '结束时间',
      dataIndex: 'V_EDATE',
      valueType: 'date',
      hideInTable: true,
      initialValue: moment(moment().year() + '-12-31').format('YYYY-MM-DD')
    },
    {
      title: '单位',
      dataIndex: 'V_DEPTCODE',
      hideInTable: true,
      valueType: 'select', //定义下拉框类型，用于Search
      fieldProps: { //相当于监听
        options: orgList, //下拉框数据
        onSelect(value: any, options: any) { //查询的时候触发
          onChangeDept(value);
        },
      },
      initialValue: '9908', //默认值
    },
    {
      title: '作业区  ',
      dataIndex: 'V_DEPTCODENEXT',
      hideInTable: true,
      valueType: 'select',
      fieldProps: {
        options: deptList,
        onSelect(value: any, options: any) {
          onChangeEquType(value);
        }
      },
      initialValue: '%',
    },
    {
      title: '设备类型',
      dataIndex: 'V_EQUTYPECODE',
      hideInTable: true,
      valueType: 'select',
      fieldProps: {
        options: eqpTypeList,
        onSelect(value: any, options: any) {
          onChangeEqpCode(options.value);
        }
      },
    },
    {
      title: '设备名称',
      dataIndex: 'V_EQUCODE',
      hideInTable: true,
      valueType: 'select',
      fieldProps: {
        options: eqpList,
        onSelect(value: any, options: any) {
          onChangeEqp(value);
        }
      },
    },
    {
      title: '操作',
      width: 150,
      hideInSearch: true,
      valueType: 'option', //操作列的类型
      render: (_, record) => [ //render渲染 record代表当前行
        <a key={record.I_PLANID} onClick={() => isShowModal(true, record.I_PLANID)}>编辑</a>,
        <a key={record.I_PLANID} onClick={() => handleRemove(record.I_PLANID)}>删除</a>,
      ],
    }
  ];

  const handleCreate = (aa: any) => {
    // 调用子组件的自定义方法getItemsValue。注意：通过  this.formRef 才能拿到数据
  };

  const routes = [
    {
      path: '',
      breadcrumbName: '特种设备管理',
    },
    {
      path: '',
      breadcrumbName: '特种设备移装管理',
    },
    {
      path: '',
      breadcrumbName: '设备移装',
    },
  ];

  return (
    // 布局标签                                                      设置显示的面包屑（自定义路径: 特种设备管理/特种设备移装管理/设备移装）
    <PageHeaderWrapper className="site-page-header" title="设备移装" breadcrumb={{routes}}>
      <ProTable      // 表格Pro组件3
        columns={columns}  // 上面定义的
        headerTitle="设备移装申请"  // 表头
        actionRef={actionRef}  // 用于触发刷新操作等，看api
        rowKey="I_PLANID"        // 表格行 key 的取值，可以是字符串或一个函数
        options={{
          density: true, // 密度
          fullScreen: true, // 全屏
          reload: true, // 刷新
          setting: true // 列设置
        }}
        pagination={{ //设置分页 ，可设置为pagination={false}不加分页
          pageSize: 20,
          current: 1
        }}
        toolBarRender={(action, {selectedRows}) => [//工具栏 与 表头headerTitle同一行 可设置为false，设置false表头无效
          <Button
            icon={<PlusOutlined/>} //图表，其他图标可去ant官网搜索icon，单击即可复制
            type="primary" //设置为主要键（蓝色）, 不加为白色
            onClick={() => { //点击事件
			  //
              isShowModal(true);
            }}>
            新建
          </Button>,
          <Button
            icon={<DownloadOutlined/>}
            type="primary"
            key="export" 
            onClick={() => {
              window.location.href = '/pmnew-specequ/equMove/exportEquMove?'
                + 'V_PERSONCODE=' + 'qxzhangzf'
                + '&V_DEPTCODE=' + orgCode
                + '&V_DEPTCODENEXT=' + (deptCode === '' || deptCode === undefined ? encodeURIComponent('%') : (deptCode === '%' ? encodeURIComponent('%') : deptCode))
                + '&V_EQUTYPECODE=' + (eqpTypeCode === '' || eqpTypeCode === undefined ? encodeURIComponent('%') : eqpTypeCode)
                + '&V_EQUTYPENAME=' + (eqpTypeCode === '' || eqpTypeCode === undefined ? encodeURIComponent('%') : eqpTypeCode)
                + '&V_EQUCODE=' + (eqpCode === '' || eqpCode === undefined ? encodeURIComponent('%') : eqpCode)
                + '&V_BDATE=' + beginDate
                + '&V_EDATE=' + endDate
                + '&V_STATUS=' + '';
            }}>
            导出
          </Button>
        ]}

        request={async (params) => { //调用请求加载表格数据， 默认自动加载 params为Search的查询条件参数
          const newParams = {};
          params.V_STATUS = '';
          params.V_PERSONCODE = 'qxzhangzf';
          params.V_EQUTYPECODE === '' ? '%' : params.V_EQUTYPECODE;
          params.V_EQUTYPENAME = params.V_EQUTYPECODE;
          (params.V_EQUCODE === '' || params.V_EQUCODE === undefined) ? '%' : params.V_EQUCODE;
          setBeginDate(params.V_BDATE);
          setEndDate(params.V_EDATE);
          setOrgCode(params.V_DEPTCODE);
          setDeptCode(params.V_DEPTCODENEXT);
          setEqpTypeCode(params.V_EQUTYPECODE);
          setEqpCode(params.V_EQUCODE);
          Object.assign(newParams, params); 
          const response = await selectEquMove({...newParams}); 
          return response;
        }}
      />
        // 三木运算符
      {
        // 模态框隐藏的时候, 不挂载组件; 模态显示时候再挂载组件, 这样是为了触发子组件的生命周期
        !isModalVisible ? (
          ''
        ) : (
          <UpdateSpecialEquMove //调用子页面UpdateSpecialEquMove，传参
            isModalVisible={isModalVisible}
            isShowModal={isShowModal}
            actionRef={actionRef}
            equMoveId={equMoveId}
            handleCreate={handleCreate}
          />
        )
      }
    </PageHeaderWrapper>
  );
};

export default connect()(Applications);
