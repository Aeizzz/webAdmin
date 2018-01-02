<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Created>2015-06-05T18:19:34Z</Created>
  <LastSaved>2017-08-11T08:23:11Z</LastSaved>
  <Version>16.00</Version>
 </DocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
  <RemovePersonalInformation/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>12645</WindowHeight>
  <WindowWidth>22260</WindowWidth>
  <WindowTopX>0</WindowTopX>
  <WindowTopY>0</WindowTopY>
  <NoAutoRecover/>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#000000"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
  <Style ss:ID="s71">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
  </Style>
  <Style ss:ID="s78">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="等线" x:CharSet="134" ss:Size="11" ss:Color="#FFFFFF"/>
   <Interior ss:Color="#5B9BD5" ss:Pattern="Solid"/>
  </Style>
  <Style ss:ID="s81">
   <Alignment ss:Horizontal="Center" ss:Vertical="Center"/>
   <Borders>
    <Border ss:Position="Bottom" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Left" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Right" ss:LineStyle="Continuous" ss:Weight="1"/>
    <Border ss:Position="Top" ss:LineStyle="Continuous" ss:Weight="1"/>
   </Borders>
   <Font ss:FontName="等线" x:CharSet="134" ss:Size="18" ss:Color="#000000"
    ss:Bold="1"/>
   <Interior ss:Color="#70AD47" ss:Pattern="Solid"/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="9" ss:ExpandedRowCount="${rowCount+2}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
   <Column ss:Index="2" ss:AutoFitWidth="0" ss:Width="88.5"/>
   <Column ss:AutoFitWidth="0" ss:Width="96.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="92.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="95.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="93"/>
   <Column ss:AutoFitWidth="0" ss:Width="152.25"/>
   <Column ss:AutoFitWidth="0" ss:Width="173.25"/>
   <Row ss:Height="23.25">
    <Cell ss:MergeAcross="8" ss:StyleID="s81"><Data ss:Type="String">汇总结果</Data></Cell>
   </Row>
   <Row>
    <Cell ss:StyleID="s78"><Data ss:Type="String">姓名</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">评委</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">评委结果</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">建议岗层</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">建议岗级</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">通过数</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">不通过数</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">部门意见</Data></Cell>
    <Cell ss:StyleID="s78"><Data ss:Type="String">薪级薪等</Data></Cell>
   </Row>
   <#list infos as pc> 
   <Row>
    <Cell ss:StyleID="s71"><Data ss:Type="String">${pc.info.userName}</Data></Cell>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"><Data ss:Type="String">${pc.passCount}</Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String">${pc.noPassCount}</Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><#if (pc.info.postLevelApplyName)?exists>${pc.info.postLevelApplyName}<#else></#if><#if (pc.info.postGradeApplyName)?exists>${pc.info.postGradeApplyName}<#else></#if></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><#if (pc.info.salaryScale)?exists>${pc.info.salaryScale}<#else></#if></Data></Cell>
   </Row>
   <#list pc.comments as com>
   <Row>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"><Data ss:Type="String">${com.committeePersonName}</Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><#if com.reviewResult == '1'>通过<#else>未通过</#if></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><#if (com.proposalPostLevelName)?exists>${com.proposalPostLevelName}<#else></#if></Data></Cell>
    <Cell ss:StyleID="s71"><Data ss:Type="String"><#if (com.proposalPostGradeName)?exists>${com.proposalPostGradeName}<#else></#if></Data></Cell>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
    <Cell ss:StyleID="s71"/>
   </Row>
   </#list>
   </#list>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageSetup>
    <Header x:Margin="0.3"/>
    <Footer x:Margin="0.3"/>
    <PageMargins x:Bottom="0.75" x:Left="0.7" x:Right="0.7" x:Top="0.75"/>
   </PageSetup>
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>600</VerticalResolution>
   </Print>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>1</ActiveRow>
     <ActiveCol>4</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
