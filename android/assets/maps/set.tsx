<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.8" tiledversion="1.8.2" name="set" tilewidth="16" tileheight="16" tilecount="9" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="2">
  <image width="16" height="16" source="tiles/ice1.bmp"/>
 </tile>
 <tile id="4">
  <image width="16" height="16" source="tiles/snow.bmp"/>
 </tile>
 <tile id="5">
  <properties>
   <property name="barrier" type="bool" value="true"/>
  </properties>
  <image width="16" height="16" source="tiles/water1.bmp"/>
 </tile>
 <tile id="6">
  <image width="16" height="16" source="tiles/water2.bmp"/>
 </tile>
 <tile id="11">
  <image width="16" height="16" source="tiles/water6.bmp"/>
 </tile>
 <tile id="16">
  <properties>
   <property name="finish" type="bool" value="true"/>
  </properties>
  <image width="16" height="16" source="tiles/finish.bmp"/>
 </tile>
 <tile id="17">
  <properties>
   <property name="check_2" type="bool" value="true"/>
  </properties>
  <image width="16" height="16" source="tiles/green_check.bmp"/>
 </tile>
 <tile id="18">
  <properties>
   <property name="check_3" type="bool" value="true"/>
  </properties>
  <image width="16" height="16" source="tiles/blue_check.bmp"/>
 </tile>
 <tile id="19">
  <properties>
   <property name="check_1" type="bool" value="true"/>
  </properties>
  <image width="16" height="16" source="tiles/red_check.bmp"/>
 </tile>
</tileset>
