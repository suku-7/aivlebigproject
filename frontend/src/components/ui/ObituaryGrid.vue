<template>
    <v-container>
        <v-snackbar
            v-model="snackbar.status"
            :timeout="snackbar.timeout"
            :color="snackbar.color"
        >
            
            <v-btn style="margin-left: 80px;" text @click="snackbar.status = false">
                Close
            </v-btn>
        </v-snackbar>
        <div class="panel">
            <div class="gs-bundle-of-buttons" style="max-height:10vh;">
                <v-btn @click="addNewRow" @class="contrast-primary-text" small color="primary">
                    <v-icon small style="margin-left: -5px;">mdi-plus</v-icon>등록
                </v-btn>
                <v-btn :disabled="!selectedRow" style="margin-left: 5px;" @click="openEditDialog()" class="contrast-primary-text" small color="primary">
                    <v-icon small>mdi-pencil</v-icon>수정
                </v-btn>
            </div>
            <div class="mb-5 text-lg font-bold"></div>
            <div class="table-responsive">
                <v-table>
                    <thead>
                        <tr>
                        <th>Id</th>
                        <th>FuneralInfoId</th>
                        <th>ObituaryTemplateId</th>
                        <th>ObituaryFilePath</th>
                        <th>ObituaryFileUrl</th>
                        <th>ObituaryStatus</th>
                        <th>ObituaryCreatedAt</th>
                        <th>CustomerId</th>
                        <th>CustomerName</th>
                        <th>CustomerRrn</th>
                        <th>CustomerPhone</th>
                        <th>DeceasedName</th>
                        <th>DeceasedAge</th>
                        <th>DeceasedDate</th>
                        <th>DeceasedBirthOfDate</th>
                        <th>DeceasedGender</th>
                        <th>DeceasedNameHanja</th>
                        <th>DeceasedRrn</th>
                        <th>DeceasedReligion</th>
                        <th>DeceasedRelationToHouseholdHead</th>
                        <th>ReportRegistrationDate</th>
                        <th>ReporterName</th>
                        <th>ReporterRrn</th>
                        <th>ReporterRelationToDeceased</th>
                        <th>ReporterAddress</th>
                        <th>ReporterPhone</th>
                        <th>ReporterEmail</th>
                        <th>SubmitterName</th>
                        <th>SubmitterRrn</th>
                        <th>FuneralCompanyName</th>
                        <th>DirectorName</th>
                        <th>DirectorPhone</th>
                        <th>FuneralHomeName</th>
                        <th>MortuaryInfo</th>
                        <th>FuneralHomeAddress</th>
                        <th>FuneralDuration</th>
                        <th>ProcessionDateTime</th>
                        <th>BurialSiteInfo</th>
                        <th>ChiefMourners</th>
                        <th>TemplateKeyword</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="FuneralInfoId">{{ val.funeralInfoId }}</td>
                            <td class="whitespace-nowrap" label="ObituaryTemplateId">{{ val.obituaryTemplateId }}</td>
                            <td class="whitespace-nowrap" label="ObituaryFilePath">{{ val.obituaryFilePath }}</td>
                            <td class="whitespace-nowrap" label="ObituaryFileUrl">{{ val.obituaryFileUrl }}</td>
                            <td class="whitespace-nowrap" label="ObituaryStatus">{{ val.obituaryStatus }}</td>
                            <td class="whitespace-nowrap" label="ObituaryCreatedAt">{{ val.obituaryCreatedAt }}</td>
                            <td class="whitespace-nowrap" label="CustomerId">{{ val.customerId }}</td>
                            <td class="whitespace-nowrap" label="CustomerName">{{ val.customerName }}</td>
                            <td class="whitespace-nowrap" label="CustomerRrn">{{ val.customerRrn }}</td>
                            <td class="whitespace-nowrap" label="CustomerPhone">{{ val.customerPhone }}</td>
                            <td class="whitespace-nowrap" label="DeceasedName">{{ val.deceasedName }}</td>
                            <td class="whitespace-nowrap" label="DeceasedAge">{{ val.deceasedAge }}</td>
                            <td class="whitespace-nowrap" label="DeceasedDate">{{ val.deceasedDate }}</td>
                            <td class="whitespace-nowrap" label="DeceasedBirthOfDate">{{ val.deceasedBirthOfDate }}</td>
                            <td class="whitespace-nowrap" label="DeceasedGender">{{ val.deceasedGender }}</td>
                            <td class="whitespace-nowrap" label="DeceasedNameHanja">{{ val.deceasedNameHanja }}</td>
                            <td class="whitespace-nowrap" label="DeceasedRrn">{{ val.deceasedRrn }}</td>
                            <td class="whitespace-nowrap" label="DeceasedReligion">{{ val.deceasedReligion }}</td>
                            <td class="whitespace-nowrap" label="DeceasedRelationToHouseholdHead">{{ val.deceasedRelationToHouseholdHead }}</td>
                            <td class="whitespace-nowrap" label="ReportRegistrationDate">{{ val.reportRegistrationDate }}</td>
                            <td class="whitespace-nowrap" label="ReporterName">{{ val.reporterName }}</td>
                            <td class="whitespace-nowrap" label="ReporterRrn">{{ val.reporterRrn }}</td>
                            <td class="whitespace-nowrap" label="ReporterRelationToDeceased">{{ val.reporterRelationToDeceased }}</td>
                            <td class="whitespace-nowrap" label="ReporterAddress">{{ val.reporterAddress }}</td>
                            <td class="whitespace-nowrap" label="ReporterPhone">{{ val.reporterPhone }}</td>
                            <td class="whitespace-nowrap" label="ReporterEmail">{{ val.reporterEmail }}</td>
                            <td class="whitespace-nowrap" label="SubmitterName">{{ val.submitterName }}</td>
                            <td class="whitespace-nowrap" label="SubmitterRrn">{{ val.submitterRrn }}</td>
                            <td class="whitespace-nowrap" label="FuneralCompanyName">{{ val.funeralCompanyName }}</td>
                            <td class="whitespace-nowrap" label="DirectorName">{{ val.directorName }}</td>
                            <td class="whitespace-nowrap" label="DirectorPhone">{{ val.directorPhone }}</td>
                            <td class="whitespace-nowrap" label="FuneralHomeName">{{ val.funeralHomeName }}</td>
                            <td class="whitespace-nowrap" label="MortuaryInfo">{{ val.mortuaryInfo }}</td>
                            <td class="whitespace-nowrap" label="FuneralHomeAddress">{{ val.funeralHomeAddress }}</td>
                            <td class="whitespace-nowrap" label="FuneralDuration">{{ val.funeralDuration }}</td>
                            <td class="whitespace-nowrap" label="ProcessionDateTime">{{ val.processionDateTime }}</td>
                            <td class="whitespace-nowrap" label="BurialSiteInfo">{{ val.burialSiteInfo }}</td>
                            <td class="whitespace-nowrap" label="ChiefMourners">{{ val.chiefMourners }}</td>
                            <td class="whitespace-nowrap" label="TemplateKeyword">{{ val.templateKeyword }}</td>
                            <v-row class="ma-0 pa-4 align-center">
                                <v-spacer></v-spacer>
                                <Icon style="cursor: pointer;" icon="mi:delete" @click="deleteRow(val)" />
                            </v-row>
                        </tr>
                    </tbody>
                </v-table>
            </div>
        </div>
        <v-col>
            <v-dialog
                v-model="openDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Obituary 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Obituary :offline="offline"
                            :isNew="!value.idx"
                            :editMode="true"
                            :inList="false"
                            v-model="newValue"
                            @add="append"
                        />
                    </v-card-text>
                </v-card>
            </v-dialog>
            <v-dialog
                v-model="editDialog"
                transition="dialog-bottom-transition"
                width="35%"
            >
                <v-card>
                    <v-toolbar
                        color="primary"
                        class="elevation-0 pa-4"
                        height="50px"
                    >
                        <div style="color:white; font-size:17px; font-weight:700;">Obituary 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <Number label="ObituaryId" v-model="selectedRow.obituaryId" :editMode="true"/>
                            <Number label="FuneralInfoId" v-model="selectedRow.funeralInfoId" :editMode="true"/>
                            <Number label="ObituaryTemplateId" v-model="selectedRow.obituaryTemplateId" :editMode="true"/>
                            <String label="ObituaryFilePath" v-model="selectedRow.obituaryFilePath" :editMode="true"/>
                            <String label="ObituaryFileUrl" v-model="selectedRow.obituaryFileUrl" :editMode="true"/>
                            <String label="ObituaryStatus" v-model="selectedRow.obituaryStatus" :editMode="true"/>
                            <Date label="ObituaryCreatedAt" v-model="selectedRow.obituaryCreatedAt" :editMode="true"/>
                            <Number label="CustomerId" v-model="selectedRow.customerId" :editMode="true"/>
                            <String label="CustomerName" v-model="selectedRow.customerName" :editMode="true"/>
                            <String label="CustomerRrn" v-model="selectedRow.customerRrn" :editMode="true"/>
                            <String label="CustomerPhone" v-model="selectedRow.customerPhone" :editMode="true"/>
                            <String label="DeceasedName" v-model="selectedRow.deceasedName" :editMode="true"/>
                            <Number label="DeceasedAge" v-model="selectedRow.deceasedAge" :editMode="true"/>
                            <Date label="DeceasedDate" v-model="selectedRow.deceasedDate" :editMode="true"/>
                            <Date label="DeceasedBirthOfDate" v-model="selectedRow.deceasedBirthOfDate" :editMode="true"/>
                            <String label="DeceasedGender" v-model="selectedRow.deceasedGender" :editMode="true"/>
                            <String label="DeceasedNameHanja" v-model="selectedRow.deceasedNameHanja" :editMode="true"/>
                            <String label="DeceasedRrn" v-model="selectedRow.deceasedRrn" :editMode="true"/>
                            <String label="DeceasedReligion" v-model="selectedRow.deceasedReligion" :editMode="true"/>
                            <String label="DeceasedRelationToHouseholdHead" v-model="selectedRow.deceasedRelationToHouseholdHead" :editMode="true"/>
                            <Date label="ReportRegistrationDate" v-model="selectedRow.reportRegistrationDate" :editMode="true"/>
                            <String label="ReporterName" v-model="selectedRow.reporterName" :editMode="true"/>
                            <String label="ReporterRrn" v-model="selectedRow.reporterRrn" :editMode="true"/>
                            <String label="ReporterRelationToDeceased" v-model="selectedRow.reporterRelationToDeceased" :editMode="true"/>
                            <String label="ReporterAddress" v-model="selectedRow.reporterAddress" :editMode="true"/>
                            <String label="ReporterPhone" v-model="selectedRow.reporterPhone" :editMode="true"/>
                            <String label="ReporterEmail" v-model="selectedRow.reporterEmail" :editMode="true"/>
                            <String label="SubmitterName" v-model="selectedRow.submitterName" :editMode="true"/>
                            <String label="SubmitterRrn" v-model="selectedRow.submitterRrn" :editMode="true"/>
                            <String label="FuneralCompanyName" v-model="selectedRow.funeralCompanyName" :editMode="true"/>
                            <String label="DirectorName" v-model="selectedRow.directorName" :editMode="true"/>
                            <String label="DirectorPhone" v-model="selectedRow.directorPhone" :editMode="true"/>
                            <String label="FuneralHomeName" v-model="selectedRow.funeralHomeName" :editMode="true"/>
                            <String label="MortuaryInfo" v-model="selectedRow.mortuaryInfo" :editMode="true"/>
                            <String label="FuneralHomeAddress" v-model="selectedRow.funeralHomeAddress" :editMode="true"/>
                            <String label="FuneralDuration" v-model="selectedRow.funeralDuration" :editMode="true"/>
                            <String label="ProcessionDateTime" v-model="selectedRow.processionDateTime" :editMode="true"/>
                            <String label="BurialSiteInfo" v-model="selectedRow.burialSiteInfo" :editMode="true"/>
                            <String label="ChiefMourners" v-model="selectedRow.chiefMourners" :editMode="true"/>
                            <String label="TemplateKeyword" v-model="selectedRow.templateKeyword" :editMode="true"/>
                            <v-divider class="border-opacity-100 my-divider"></v-divider>
                            <v-layout row justify-end>
                                <v-btn
                                    width="64px"
                                    color="primary"
                                    @click="save"
                                >
                                    수정
                                </v-btn>
                            </v-layout>
                        </div>
                    </v-card-text>
                </v-card>
            </v-dialog>
        </v-col>
    </v-container>
</template>

<script>
import { ref } from 'vue';
import { useTheme } from 'vuetify';
import BaseGrid from '../base-ui/BaseGrid.vue'


export default {
    name: 'obituaryGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'obituaries',
    }),
    watch: {
    },
    methods:{
    }
}

</script>