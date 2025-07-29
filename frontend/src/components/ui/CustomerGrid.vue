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
                        <th>CustomerLoginId</th>
                        <th>CustomerLoginPassword</th>
                        <th>CustomerRole</th>
                        <th>CustomerName</th>
                        <th>CustomerRrn</th>
                        <th>CustomerAge</th>
                        <th>CustomerGender</th>
                        <th>CustomerAddress</th>
                        <th>CustomerEmail</th>
                        <th>CustomerPhone</th>
                        <th>CustomerJob</th>
                        <th>CustomerHasChildren</th>
                        <th>CustomerIsMarried</th>
                        <th>CustomerDiseaseList</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(val, idx) in value" 
                            @click="changeSelectedRow(val)"
                            :key="val"  
                            :style="val === selectedRow ? 'background-color: rgb(var(--v-theme-primary), 0.2) !important;':''"
                        >
                            <td class="font-semibold">{{ idx + 1 }}</td>
                            <td class="whitespace-nowrap" label="CustomerLoginId">{{ val.customerLoginId }}</td>
                            <td class="whitespace-nowrap" label="CustomerLoginPassword">{{ val.customerLoginPassword }}</td>
                            <td class="whitespace-nowrap" label="CustomerRole">{{ val.customerRole }}</td>
                            <td class="whitespace-nowrap" label="CustomerName">{{ val.customerName }}</td>
                            <td class="whitespace-nowrap" label="CustomerRrn">{{ val.customerRrn }}</td>
                            <td class="whitespace-nowrap" label="CustomerAge">{{ val.customerAge }}</td>
                            <td class="whitespace-nowrap" label="CustomerGender">{{ val.customerGender }}</td>
                            <td class="whitespace-nowrap" label="CustomerAddress">{{ val.customerAddress }}</td>
                            <td class="whitespace-nowrap" label="CustomerEmail">{{ val.customerEmail }}</td>
                            <td class="whitespace-nowrap" label="CustomerPhone">{{ val.customerPhone }}</td>
                            <td class="whitespace-nowrap" label="CustomerJob">{{ val.customerJob }}</td>
                            <td class="whitespace-nowrap" label="CustomerHasChildren">{{ val.customerHasChildren }}</td>
                            <td class="whitespace-nowrap" label="CustomerIsMarried">{{ val.customerIsMarried }}</td>
                            <td class="whitespace-nowrap" label="CustomerDiseaseList">{{ val.customerDiseaseList }}</td>
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
                        <div style="color:white; font-size:17px; font-weight:700;">Customer 등록</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <Customer :offline="offline"
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
                        <div style="color:white; font-size:17px; font-weight:700;">Customer 수정</div>
                        <v-spacer></v-spacer>
                        <v-icon
                            color="white"
                            small
                            @click="closeDialog()"
                        >mdi-close</v-icon>
                    </v-toolbar>
                    <v-card-text>
                        <div>
                            <Number label="CustomerId" v-model="selectedRow.customerId" :editMode="true"/>
                            <String label="CustomerLoginId" v-model="selectedRow.customerLoginId" :editMode="true"/>
                            <String label="CustomerLoginPassword" v-model="selectedRow.customerLoginPassword" :editMode="true"/>
                            <String label="CustomerRole" v-model="selectedRow.customerRole" :editMode="true"/>
                            <String label="CustomerName" v-model="selectedRow.customerName" :editMode="true"/>
                            <String label="CustomerRrn" v-model="selectedRow.customerRrn" :editMode="true"/>
                            <Number label="CustomerAge" v-model="selectedRow.customerAge" :editMode="true"/>
                            <String label="CustomerGender" v-model="selectedRow.customerGender" :editMode="true"/>
                            <String label="CustomerAddress" v-model="selectedRow.customerAddress" :editMode="true"/>
                            <String label="CustomerEmail" v-model="selectedRow.customerEmail" :editMode="true"/>
                            <String label="CustomerPhone" v-model="selectedRow.customerPhone" :editMode="true"/>
                            <String label="CustomerJob" v-model="selectedRow.customerJob" :editMode="true"/>
                            <String label="CustomerHasChildren" v-model="selectedRow.customerHasChildren" :editMode="true"/>
                            <String label="CustomerIsMarried" v-model="selectedRow.customerIsMarried" :editMode="true"/>
                            <String label="CustomerDiseaseList" v-model="selectedRow.customerDiseaseList" :editMode="true"/>
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
    name: 'customerGrid',
    mixins:[BaseGrid],
    components:{
    },
    data: () => ({
        path: 'customers',
    }),
    watch: {
    },
    methods:{
    }
}

</script>