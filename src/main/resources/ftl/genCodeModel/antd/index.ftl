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
import View${module} from './components/View${module}';
import { ProFormInstance } from '@ant-design/pro-form';

/* React.FC<>的在typescript使用的一个泛型，FC就是FunctionComponent的缩写，是函数组件，在这个泛型里面可以使用useState */
export const Applications = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);<#if hasView>
  const [isView${module}ModalVisible, setIsView${module}ModalVisible] = useState(false);</#if>
  const [${module?uncap_first}Id, set${module}Id] = useState(undefined);
  const actionRef = useRef<ActionType>();
  const formRef = useRef<ProFormInstance>();

  /**
   * 控制模态框显示和隐藏
   */
  const isShowModal = (show: boolean | ((prevState: boolean) => boolean), id = undefined) => {
        set${module}Id(id);
        setIsModalVisible(show);
  };
<#if hasView>

  /**
   * 查看模态框显示和隐藏
   */
  const is${module}ShowModal = (show: boolean | ((prevState: boolean) => boolean), id = undefined) => {
        set${module}Id(id);
        setIsView${module}ModalVisible(show);
    };
</#if>

  //useEffect参数为空数组时仅初始化执行一次
  useEffect(() => {

  }, []);

  //删除${moduleDesc}
  const handleRemove = async (id: any) => {
    const hide = message.loading('正在删除');
    if (!id) return true;
    try {
      await delete${module}({
        ${antd_primary}: id
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

  const columns: ProColumns[] = [  //定义 Protable的列 columns放在Protable
    {
      title: '序号',
      width: 50,
      render:(text,record,index)=><#noparse>`${index+1}`</#noparse>
    }, ${antd_tableParams!}, {
      title: '操作',
      width: 150,
      hideInSearch: true,
      valueType: 'option',  //操作列的类型
      render: (_, record) => [   //render渲染 record代表当前行
        <a key={record.${antd_primary}} onClick={() => isShowModal(true, record.${antd_primary})}>编辑</a>,
        <a key={record.${antd_primary}} onClick={() => handleRemove(record.${antd_primary})}>删除</a>
      ]
    }
  ];

  const routes = [
    {
      path: '',
      breadcrumbName: '一级'
    }, {
      path: '',
      breadcrumbName: '二级'
    }, {
      path: '',
      breadcrumbName: '${moduleDesc}'
    }
  ];

  return (
    // 布局标签
    <PageHeaderWrapper className="site-page-header" title="${moduleDesc}" breadcrumb={{routes}}>
      <ProTable
        columns={columns}// 上面定义的表格列
        headerTitle="${moduleDesc}列表" // 表头
        actionRef={actionRef} // 用于触发刷新操作等，看api
        formRef={formRef}
        rowKey="${antd_primary}"// 表格行 key 的取值，可以是字符串或一个函数
        manualRequest={false} // 是否需要手动触发首次请求
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
            icon={<PlusOutlined/>}  //图标，其他图标可去ant官网搜索icon，单击即可复制
            type="primary"   //设置为主要键（蓝色）, 不加为白色,只能有一个type="primary"
            onClick={() => {  //点击事件
              isShowModal(true);
            }}>
            新建
          </Button>
        ]}
        search={{
          defaultCollapsed: false,
          optionRender: (searchConfig, formProps, dom) => [
            ...dom.reverse()<#if hasExport>,
            <Button
              //icon={<DownloadOutlined/>}
              key="export"
              onClick={() => {
                window.location.href = '/${package?substring(package?last_index_of(".")+1)?lower_case}/export${module}<#if antd_exportParamUrl?? && antd_exportParamUrl?length gt 0>?' +
                  ${antd_exportParamUrl};<#else>';</#if>
              }}>
              导出
            </Button></#if>
          ]
        }}
        request={async (params) => {   //调用请求加载表格数据， 默认自动加载 params为Search的查询条件参数
          const newParams = {};
          //可对params传参进行进一步处理后再调用查询
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
<#if hasView>

      {
        !isView${module}ModalVisible ? (
          ''
        ) : (
          <View${module}
            isView${module}ModalVisible={isView${module}ModalVisible}
            is${module}ShowModal={is${module}ShowModal}
            actionRef={actionRef}
            ${module?uncap_first}Id={${module?uncap_first}Id}
          />
        )
      }
</#if>
    </PageHeaderWrapper>
  );
};

export default connect()(Applications);
