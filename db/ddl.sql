create database batch;

create table if not exists batch.BATCH_JOB_EXECUTION_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists batch.BATCH_JOB_INSTANCE
(
    JOB_INSTANCE_ID bigint       not null
    primary key,
    VERSION         bigint       null,
    JOB_NAME        varchar(100) not null,
    JOB_KEY         varchar(32)  not null,
    constraint JOB_INST_UN
    unique (JOB_NAME, JOB_KEY)
    );

create table if not exists batch.BATCH_JOB_EXECUTION
(
    JOB_EXECUTION_ID bigint        not null
    primary key,
    VERSION          bigint        null,
    JOB_INSTANCE_ID  bigint        not null,
    CREATE_TIME      datetime(6)   not null,
    START_TIME       datetime(6)   null,
    END_TIME         datetime(6)   null,
    STATUS           varchar(10)   null,
    EXIT_CODE        varchar(2500) null,
    EXIT_MESSAGE     varchar(2500) null,
    LAST_UPDATED     datetime(6)   null,
    constraint JOB_INST_EXEC_FK
    foreign key (JOB_INSTANCE_ID) references batch.BATCH_JOB_INSTANCE (JOB_INSTANCE_ID)
    );

create table if not exists batch.BATCH_JOB_EXECUTION_CONTEXT
(
    JOB_EXECUTION_ID   bigint        not null
    primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint JOB_EXEC_CTX_FK
    foreign key (JOB_EXECUTION_ID) references batch.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists batch.BATCH_JOB_EXECUTION_PARAMS
(
    JOB_EXECUTION_ID bigint        not null,
    PARAMETER_NAME   varchar(100)  not null,
    PARAMETER_TYPE   varchar(100)  not null,
    PARAMETER_VALUE  varchar(2500) null,
    IDENTIFYING      char          not null,
    constraint JOB_EXEC_PARAMS_FK
    foreign key (JOB_EXECUTION_ID) references batch.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists batch.BATCH_JOB_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists batch.BATCH_STEP_EXECUTION
(
    STEP_EXECUTION_ID  bigint        not null
    primary key,
    VERSION            bigint        not null,
    STEP_NAME          varchar(100)  not null,
    JOB_EXECUTION_ID   bigint        not null,
    CREATE_TIME        datetime(6)   not null,
    START_TIME         datetime(6)   null,
    END_TIME           datetime(6)   null,
    STATUS             varchar(10)   null,
    COMMIT_COUNT       bigint        null,
    READ_COUNT         bigint        null,
    FILTER_COUNT       bigint        null,
    WRITE_COUNT        bigint        null,
    READ_SKIP_COUNT    bigint        null,
    WRITE_SKIP_COUNT   bigint        null,
    PROCESS_SKIP_COUNT bigint        null,
    ROLLBACK_COUNT     bigint        null,
    EXIT_CODE          varchar(2500) null,
    EXIT_MESSAGE       varchar(2500) null,
    LAST_UPDATED       datetime(6)   null,
    constraint JOB_EXEC_STEP_FK
    foreign key (JOB_EXECUTION_ID) references batch.BATCH_JOB_EXECUTION (JOB_EXECUTION_ID)
    );

create table if not exists batch.BATCH_STEP_EXECUTION_CONTEXT
(
    STEP_EXECUTION_ID  bigint        not null
    primary key,
    SHORT_CONTEXT      varchar(2500) not null,
    SERIALIZED_CONTEXT text          null,
    constraint STEP_EXEC_CTX_FK
    foreign key (STEP_EXECUTION_ID) references batch.BATCH_STEP_EXECUTION (STEP_EXECUTION_ID)
    );

create table if not exists batch.BATCH_STEP_EXECUTION_SEQ
(
    ID         bigint not null,
    UNIQUE_KEY char   not null,
    constraint UNIQUE_KEY_UN
    unique (UNIQUE_KEY)
    );

create table if not exists batch.tb_restaurant
(
    id                                      int auto_increment comment '고유 식별자 (자동 생성)'
    primary key,
    approval_cancel_date                    varchar(255) null comment '인허가취소일자',
    approval_date                           varchar(255) null comment '인허가일자',
    business_name                           varchar(255) null comment '사업장명',
    business_status_code                    varchar(255) null comment '영업 상태 구분 코드',
    business_status_name                    varchar(255) null comment '영업 상태명',
    closure_date                            varchar(255) null comment '폐업일자',
    data_update_date                        varchar(255) null comment '데이터 갱신일자',
    data_update_type                        varchar(255) null comment '데이터 갱신 구분',
    detailed_business_status_code           varchar(255) null comment '상세 영업 상태 코드',
    detailed_business_status_name           varchar(255) null comment '상세 영업 상태명',
    industry_type_name                      varchar(255) null comment '업태 구분명',
    last_modified_time                      varchar(255) null comment '최종 수정 시점',
    local_government_code                   varchar(255) null comment '개방 자치 단체 코드',
    location_area                           varchar(255) null comment '소재지 면적',
    location_full_address                   varchar(255) null comment '소재지 전체주소',
    location_phone_number                   varchar(255) null comment '소재지 전화',
    location_postal_code                    varchar(255) null comment '소재지 우편번호',
    management_number                       varchar(255) null comment '관리 번호',
    reopening_date                          varchar(255) null comment '재개업일자',
    road_full_address                       varchar(255) null comment '도로명 전체주소',
    road_postal_code                        varchar(255) null comment '도로명 우편번호',
    service_id                              varchar(255) null comment '개방 서비스 아이디',
    service_name                            varchar(255) null comment '개방 서비스명',
    suspension_end_date                     varchar(255) null comment '휴업 종료일자',
    suspension_start_date                   varchar(255) null comment '휴업 시작일자',
    traditional_business_designation_number varchar(255) null comment '전통업소 지정 번호',
    constraint UK3ee6aa4qx7nae1uv59irvncn3
    unique (management_number)
    );

create index idx_management_number
    on batch.tb_restaurant (management_number);

