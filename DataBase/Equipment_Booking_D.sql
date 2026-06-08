-- MySQL dump 10.13  Distrib 9.5.0, for macos15 (arm64)
--
-- Host: localhost    Database: Equipment_Booking_DB
-- ------------------------------------------------------
-- Server version	9.5.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ '8feede06-e475-11f0-b410-92bdfcb69ce9:1-521';

--
-- Table structure for table `Equipments`
--

DROP TABLE IF EXISTS `Equipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Equipments` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(100) NOT NULL,
  `introduction` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ImageUrl` varchar(888) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `number` int NOT NULL,
  `spec` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储设备相关信息的表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Equipments`
--

LOCK TABLES `Equipments` WRITE;
/*!40000 ALTER TABLE `Equipments` DISABLE KEYS */;
INSERT INTO `Equipments` VALUES ('01001','反应釜系统','配备搅拌、加热、冷却、压力控制等功能，用于化学反应工艺研究。','化学反应工程实验室301室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%96%E5%AD%A6%E5%B7%A5%E7%A8%8B/%E5%8F%8D%E5%BA%94%E9%87%9C%E7%B3%BB%E7%BB%9F.png',0,'容积：50 mL～100 L（可选）\n压力/温度：≤10 MPa / ‑20～300 ℃\n材质：316L 不锈钢或搪玻璃\n搅拌：变频调速 0～1500 rpm，磁力密封\n'),('01002','旋转蒸发仪','用于溶液浓缩、溶剂回收、反应产物分离。','化学工程楼C102室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%96%E5%AD%A6%E5%B7%A5%E7%A8%8B/%E6%97%8B%E8%BD%AC%E8%92%B8%E5%8F%91%E4%BB%AA.png',0,'蒸发/收集瓶：250 mL（标配，可扩展）\n转速：0～280 rpm，无级调速\n水浴锅：室温＋5～99 ℃，控温精度 ±1 ℃\n真空度：≤‑0.098 MPa（配真空泵）\n'),('01003','高效液相色谱仪(HPLC)','采用高压输液系统，对混合物进行分离、分析和制备。配备紫外检测器、荧光检测器，适用于有机化合物、药物、环境污染物等分析。','化学工程楼A201室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%96%E5%AD%A6%E5%B7%A5%E7%A8%8B/%E9%AB%98%E6%95%88%E6%B6%B2%E7%9B%B8%E8%89%B2%E8%B0%B1%E4%BB%AA%28HPLC%29.png',10,'流量范围：0.001～10 mL/min，精度 ≤0.1 % RSD\n最高压力：≥40 MPa（400 bar）\n进样量：0.1～100 μL，自动进样\n检测器：UV‑Vis 190～700 nm\n'),('02001','全自动生化分析仪','全自动生化分析仪可自动完成加样、反应、检测、数据分析；用于临床、科研等生化指标测定。','医疗实验室201室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%BB%E5%AD%A6%E5%AE%9E%E9%AA%8C/%E5%85%A8%E8%87%AA%E5%8A%A8%E7%94%9F%E5%8C%96%E5%88%86%E6%9E%90%E4%BB%AA.png',1,'测试速度：≥800 测试/小时\n样本/试剂位：≥100 / ≥80，试剂冷藏\n反应温控：37 ℃±0.1 ℃，比色杯系统\n光学系统：后分光，吸光度 0～3.0 Abs\n'),('02002','CO2细胞培养箱','湿度控制，可编程，适用于哺乳动物细胞、植物细胞、微生物培养。','细胞培养室201室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%BB%E5%AD%A6%E5%AE%9E%E9%AA%8C/CO2%E7%BB%86%E8%83%9E%E5%9F%B9%E5%85%BB%E7%AE%B1.png',7,'容积：160～240 L\nCO₂/温度控制：0～20 %（±0.1 %）/ 37 ℃±0.1 ℃\n湿度：≥95 % RH\n灭菌：HEPA 过滤 + 高温或紫外消毒\n'),('02756','高速冷冻离心机','临床检验：血液分层（分离血清/血浆）、尿液沉渣分析、免疫沉淀物收集。 分子生物学：DNA/RNA 提取、质粒纯化、PCR 产物回收、病毒颗粒富集。','医学实验楼301','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E5%8C%BB%E5%AD%A6%E5%AE%9E%E9%AA%8C/%E9%AB%98%E9%80%9F%E5%86%B7%E5%86%BB%E7%A6%BB%E5%BF%83%E6%9C%BA.png',6,'最高转速：20,000 ~ 21,000 rpm（微量机型可达 25,000+ rpm） 最大相对离心力 (RCF)：30,000 ×g ~ 50,000 ×g'),('03001','数控加工中心','配备自动换刀系统，可进行铣削、钻孔、攻丝等加工。','先进制造实验室301室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9C%BA%E6%A2%B0%E5%B7%A5%E7%A8%8B/%E6%95%B0%E6%8E%A7%E5%8A%A0%E5%B7%A5%E4%B8%AD%E5%BF%83.png',9,'工作台：≥1000 mm × 500 mm\n行程/主轴：X/Y/Z ≥800/500/500 mm，转速 ≥8000 rpm\n定位精度：≤0.005 mm/300 mm\n刀库：≥16 把，自动换刀\n'),('03002','三坐标测量机(CMM)','配备接触式探头和扫描探头，用于复杂零件尺寸、形状、位置精度测量。','精密测量实验室101室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9C%BA%E6%A2%B0%E5%B7%A5%E7%A8%8B/%E4%B8%89%E5%9D%90%E6%A0%87%E6%B5%8B%E9%87%8F%E6%9C%BA%28CMM%29.png',9,'测量行程：≥500×700×400 mm\n精度：≤(1.5 +L/250) μm\n探测系统：接触式触发或扫描测头\n软件：支持 CAD 导入及自动测量\n'),('04001','扫描电子显微镜(SEM)','高分辨率成像，可观察材料表面形貌、断面结构、元素分布等。','材料分析中心101室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9D%90%E6%96%99%E7%A7%91%E5%AD%A6/%E6%89%AB%E6%8F%8F%E7%94%B5%E5%AD%90%E6%98%BE%E5%BE%AE%E9%95%9C.png',9,'分辨率：≤3.0 nm @30 kV\n加速电压：0.5～30 kV\n放大倍数：20～1 000 000×\n能谱仪 (EDS)：元素范围 B～U，分辨率 ≤129 eV\n'),('04002','X射线衍射仪(XRD)','用于材料晶体结构分析，可测定晶格常数、物相鉴定、残余应力、织构等。','材料分析中心102室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9D%90%E6%96%99%E7%A7%91%E5%AD%A6/x%E5%B0%84%E7%BA%BF%E8%A1%8D%E5%B0%84%E4%BB%AA.png',9,'功率：≥3 kW，Cu 靶\n测角仪：2θ 范围 ‑10～140°，重现性 ≤0.0001°\n探测器：闪烁计数器或阵列探测器\n功能：物相检索、定量分析\n'),('04003','真空溅射镀膜机','用于制备金属、介质、半导体薄膜，可进行磁控溅射、热蒸发、电子束蒸发等多种镀膜方式。','薄膜技术实验室301室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9D%90%E6%96%99%E7%A7%91%E5%AD%A6/%E7%9C%9F%E7%A9%BA%E6%BA%85%E5%B0%84%E9%95%80%E8%86%9C%E6%9C%BA.png',9,'极限真空：≤5×10⁻⁴ Pa\n靶位：≥2 个，支持 DC/RF 溅射\n基片尺寸：≥Φ100 mm，可加热 ≥500 ℃\n膜厚监控：石英晶体，分辨率 0.1 nm\n'),('04004','原子吸收光谱仪(AAS)','用于测定水、土壤、生物样品中重金属元素含量，配备自动进样器。','环境分析中心202室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E6%9D%90%E6%96%99%E7%A7%91%E5%AD%A6/%E5%8E%9F%E5%AD%90%E5%90%B8%E6%94%B6%E5%85%89%E8%B0%B1%E4%BB%AA%28AAS%29.png',9,'波长范围：190～900 nm\n灯位：≥6 灯自动切换\n火焰/石墨炉：全钛燃烧头，石墨炉升温 ≥3000 ℃/s\n背景校正：氘灯或塞曼校正\n'),('05001','激光干涉仪','用于精密长度测量、表面形貌检测，可进行动态和静态测量。','物理实验楼A101室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%89%A9%E7%90%86%E5%AE%9E%E9%AA%8C/%E6%BF%80%E5%85%89%E5%B9%B2%E6%B6%89%E4%BB%AA.png',9,'测量范围：≥40 m，分辨率 0.001 μm\n精度：±0.5 ppm\n参数：位移、角度、直线度、平面度\n补偿：空气温/压/湿实时折射率补偿\n'),('05002','低温恒温器','配备液氦循环系统，用于低温物理实验、超导材料测试等。','低温物理实验室301室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%89%A9%E7%90%86%E5%AE%9E%E9%AA%8C/%E4%BD%8E%E6%B8%A9%E6%81%92%E6%B8%A9%E5%99%A8.png',9,'温度范围：1.5～300 K（液氦）或 4.2～300 K（制冷机）\n控温稳定性：±0.01 K @4.2 K\n真空度：≤1×10⁻⁵ Pa\n样品台：可加热 ≥500 ℃，带低温引线\n'),('05003','光学平台系统','配备光学元件支架、调节架、激光器、透镜、反射镜等，用于光学实验搭建。','光学实验室501室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%89%A9%E7%90%86%E5%AE%9E%E9%AA%8C/%E5%85%89%E5%AD%A6%E5%B9%B3%E5%8F%B0%E7%B3%BB%E7%BB%9F.png',9,'尺寸：≥1500×1000×200 mm\n固有频率：≤1 Hz\n安装孔：M6 螺纹，25 mm×25 mm 网格\n支撑：气浮隔振支脚，自带水平仪\n'),('06001','总有机碳分析仪(TOC)','用于测定水样中总有机碳、总无机碳、总碳含量，适用于环境水质监测。','环境分析中心102室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%8E%AF%E5%A2%83%E7%A7%91%E5%AD%A6/%E6%80%BB%E6%9C%89%E6%9C%BA%E7%A2%B3%E5%88%86%E6%9E%90%E4%BB%AA%28TOC%29.png',9,'检测原理：高温催化燃烧氧化\n检测范围：0～30000 ppm\n精度：≤2 % RSD @100 ppm\n分析时间：≤5 min/样，自动进样\n'),('06002','气相色谱-质谱联用仪(GC-MS)','结合色谱的高分离能力和质谱的高鉴别能力，适用于复杂环境样品中挥发性有机物的定性和定量分析，检测限可达ppt级。','环境分析中心101室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%8E%AF%E5%A2%83%E7%A7%91%E5%AD%A6/%E6%B0%94%E7%9B%B8%E8%89%B2%E8%B0%B1-%E8%B4%A8%E8%B0%B1%E8%81%94%E7%94%A8%E4%BB%AA%28GC-MS%29.png',9,'GC：柱温箱 ≥450 ℃，升温 ≥70 ℃/min\nMS：质量范围 1.5～1050 amu，EI 源\n真空系统：分子泵，极限真空 ≤1×10⁻⁴ Pa\n软件：NIST 谱库检索、定量分析\n'),('07001','PCR仪','支持梯度PCR、降落PCR、热启动PCR等多种程序。','生物技术中心101室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%94%9F%E7%89%A9%E6%8A%80%E6%9C%AF/PCR%E4%BB%AA.png',9,'样本容量：96 孔或 384 孔\n温度范围：4～99 ℃，升降温 ≥3 ℃/s\n均一性：±0.2 ℃ @95 ℃\n功能：支持梯度 PCR\n'),('07002','离心机','分离液体与固体颗粒或不同密度的液体（如细胞、蛋白质、核酸沉淀）','医疗实验室202室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%94%9F%E7%89%A9%E6%8A%80%E6%9C%AF/%E7%A6%BB%E5%BF%83%E6%9C%BA.png',5,'转速：≥15000 rpm（微量）或 ≥6000 rpm（大容量）\nRCF：≥21000×g（微量）或 ≥6000×g（大容量）\n转子：角转子、水平转子自动识别\n制冷：最低 ≤‑20 ℃（冷冻型）\n'),('08001','数字示波器','具有多种触发功能和自动测量，适用于电子电路信号观测与分析。','电子信息楼406室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%94%B5%E5%AD%90%E4%BF%A1%E6%81%AF/%E6%95%B0%E5%AD%97%E5%AD%98%E5%82%A8%E7%A4%BA%E6%B3%A2%E5%99%A8.jpg',9,'带宽：≥100 MHz\n采样率：≥1 GS/s 实时采样\n存储深度：≥10 Mpts/通道\n通道：2 通道 + 外部触发\n'),('08002','频谱分析仪','适用于射频信号分析、无线通信测试。','通信工程实验室301室','https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E8%AE%BE%E5%A4%87%E5%9B%BE%E7%89%87/%E7%94%B5%E5%AD%90%E4%BF%A1%E6%81%AF/%E9%A2%91%E8%B0%B1%E5%88%86%E6%9E%90%E4%BB%AA.png',9,'频率范围：9 kHz～3 GHz（可扩展）\nRBW：1 Hz～3 MHz\nDANL：≤‑150 dBm/Hz @1 GHz\n接口：USB、LAN，支持 SCPI\n');
/*!40000 ALTER TABLE `Equipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reservations`
--

DROP TABLE IF EXISTS `Reservations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Reservations` (
  `id` varchar(20) NOT NULL,
  `apply_id` varchar(20) NOT NULL,
  `admin_id` varchar(20) NOT NULL,
  `device_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `purpose` varchar(500) NOT NULL,
  `status` int NOT NULL DEFAULT '0',
  `reject_reason` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `Reservations_Equipments_FK` (`device_id`),
  KEY `Reservations_Users_FK` (`apply_id`),
  KEY `Reservations_Users_FK_1` (`admin_id`),
  CONSTRAINT `Reservations_Equipments_FK` FOREIGN KEY (`device_id`) REFERENCES `Equipments` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Reservations_Users_FK` FOREIGN KEY (`apply_id`) REFERENCES `Users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Reservations_Users_FK_1` FOREIGN KEY (`admin_id`) REFERENCES `Users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储预约信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reservations`
--

LOCK TABLES `Reservations` WRITE;
/*!40000 ALTER TABLE `Reservations` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reservations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Users` (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `is_admin` int NOT NULL DEFAULT '0' COMMENT '是否为管理员',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES ('01000260501','张伟','260501',1,'2026-05-10 00:09:00','2026-05-10 01:04:39'),('01000260502','王芳','260502',1,'2026-05-10 00:09:36','2026-05-10 01:04:40'),('01000260503','刘啊毛','260503',1,'2026-05-10 00:09:00','2026-05-10 01:04:39'),('02000260501','刘洋','260501',1,'2026-05-10 00:10:18','2026-05-10 01:04:40'),('02000260502','赵刚','260502',1,'2026-05-10 00:10:18','2026-05-10 01:04:40'),('02000260503','王小花','123456',1,'2026-05-22 01:16:56','2026-05-22 01:16:56'),('03000260501','周涛','260501',1,'2026-05-10 00:13:13','2026-05-10 01:04:40'),('03000260502','吴弘','260502',1,'2026-05-10 00:14:09','2026-05-10 01:04:40'),('04000260501','王丽','260501',1,'2026-05-10 00:15:10','2026-05-10 01:04:40'),('04000260502','吴霞','260502',1,'2026-05-10 00:15:10','2026-05-10 01:04:40'),('05000260501','陈晨','260501',1,'2026-05-10 00:16:11','2026-05-10 01:04:40'),('05000260502','杨燕','260502',1,'2026-05-10 00:16:11','2026-05-10 01:04:40'),('06000260501','赵彩','260501',1,'2026-05-10 00:17:11','2026-05-10 01:04:40'),('06000260502','李明','260502',1,'2026-05-10 00:17:11','2026-05-10 01:04:40'),('07000260501','刘敏','260501',1,'2026-05-10 00:18:07','2026-05-10 01:04:40'),('07000260502','赵理','260502',1,'2026-05-10 00:18:07','2026-05-10 01:04:40'),('08000260501','李芳','260501',1,'2026-05-10 00:18:57','2026-05-10 01:04:40'),('08000260502','贺万','260502',1,'2026-05-10 00:18:57','2026-05-10 01:04:40'),('24218040000','xiaoA','123456',0,'2026-05-22 01:17:34','2026-05-22 01:17:34'),('24218040102','李四','040102',0,'2026-04-16 04:01:08','2026-05-10 01:04:40'),('24218040103','王五','040103',0,'2026-04-16 04:01:08','2026-05-10 01:04:40'),('24218040104','赵六','040104',0,'2026-04-16 04:01:08','2026-05-10 01:04:40'),('24218040105','张七','040105',0,'2026-04-16 04:01:08','2026-05-10 01:04:40'),('24218040167','刘三','123456',0,'2026-04-16 04:01:08','2026-05-15 06:20:29');
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-04  8:36:34
