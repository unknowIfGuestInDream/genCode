import {PlusOutlined} from '@ant-design/icons';
import {Button, message} from 'antd';
import {useEffect, useRef, useState} from 'react';
// @ts-ignore
import {connect} from 'umi';
import {PageHeaderWrapper} from '@ant-design/pro-layout';
import ProTable, {ActionType, ProColumns} from '@ant-design/pro-table';
import {select${module}, delete${module}} from './service';
import 'moment/locale/zh-cn'
import Update${module} from "./components/Update${module}";
import moment from "moment";

/* React.FC<>的在typescript使用的一个泛型，FC就是FunctionComponent的缩写，是函数组件，在这个泛型里面可以使用useState */
export const Applications = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [${module?uncap_first}Id, set${module}Id] = useState(undefined);
  const actionRef = useRef<ActionType>();

  /**
   * 控制模态框显示和隐藏
   */
  const isShowModal = (show: boolean | ((prevState: boolean) => boolean), id = undefined) => {
        set${module}Id(id);
        setIsModalVisible(show);
      };

  //删除${moduleDesc}
  const handleRemove = async (I_PLANID: any) => {
    const hide = message.loading('正在删除');
    if (!I_PLANID) return true;
    try {
      await delete${module}({
        ${primary}: I_PLANID
      });
      hide();
      message.success('删除成功，即将刷新');
      actionRef.current?.reloadAndRest?.(); //刷新Protable
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试');
      return false;
    }
  };

  //useEffect仅初始化执行一次,[] 空数组 执行一次
  useEffect(() => {

  }, []);

  const columns: ProColumns[] = [  //定义 Protable的列 columns放在Protable
    {
      title: '序号',  //列名
      dataIndex: 'RN',  //返回的字段
      width: 60,   //宽度
      hideInSearch: true  // 在Search中隐藏，不做查询条件
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
      valueType: 'date',   //定义时间类型，用于Search中
      hideInTable: true,  //在Protable中隐藏，不显示
      initialValue: moment(moment().year() + '-01-01').format('YYYY-MM-DD')   //设置默认值
    },
    {
      title: '结束时间',
      dataIndex: 'V_EDATE',
      valueType: 'date',
      hideInTable: true,
      initialValue: moment(moment().year() + '-12-31').format('YYYY-MM-DD')
    }, {
      title: '操作',
      width: 150,
      hideInSearch: true,
      valueType: 'option',  //操作列的类型
      render: (_, record) => [   //render渲染 record代表当前行
        <a key={record.I_PLANID} onClick={() => isShowModal(true, record.I_PLANID)}>编辑</a>,
        <a key={record.I_PLANID} onClick={() => handleRemove(record.I_PLANID)}>删除</a>,
      ],
    }
  ];

  const routes = [
    {
      path: '',
      breadcrumbName: '一级',
    },
    {
      path: '',
      breadcrumbName: '二级',
    },
    {
      path: '',
      breadcrumbName: '三级',
    },
  ];

  return (
      // 布局标签
      <PageHeaderWrapper className="site-page-header" title="设备移装" breadcrumb={{routes}}>
        <ProTable      // 表格Pro组件3
            columns={columns}  // 上面定义的
            headerTitle="${moduleDesc}"  // 表头
            actionRef={actionRef}  // 用于触发刷新操作等，看api
            rowKey="${primary}"        // 表格行 key 的取值，可以是字符串或一个函数
            manualRequest={false} //是否需要手动触发首次请求
            options={{
              density: true, // 密度
              fullScreen: true, // 全屏
              reload: true, // 刷新
              setting: true // 列设置
            }}
            pagination={{  //设置分页 ，可设置为pagination={false}不加分页
              pageSize: 20,
              current: 1
            }}
            toolBarRender={(action, {selectedRows}) => [ //工具栏 与 表头headerTitle同一行 可设置为false，设置false表头无效
              <Button
                  icon={<PlusOutlined/>}  //图表，其他图标可去ant官网搜索icon，单击即可复制
                  type="primary"   //设置为主要键（蓝色）, 不加为白色
                  onClick={() => {  //点击事件
                    isShowModal(true);
                  }}>
                新建
              </Button>
            ]}

            request={async (params) => {   //调用请求加载表格数据， 默认自动加载 params为Search的查询条件参数
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
              return await select${module}({...newParams});
            }}
        />

        {
          // 模态框隐藏的时候, 不挂载组件; 模态显示时候再挂载组件, 这样是为了触发子组件的生命周期
          !isModalVisible ? (
              ''
          ) : (
              <Update${module}
                  isModalVisible={isModalVisible}
                  isShowModal={isShowModal}
                  actionRef={actionRef}
                  ${module?uncap_first}Id={${module?uncap_first}Id}
              />
          )
        }
      </PageHeaderWrapper>
  );
};

export default connect()(Applications);
