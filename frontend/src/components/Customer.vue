<template>
    <div>
        <Number
            label="CustomerId"
            v-model="value.customerId"
            :editMode="editMode"
        />
        <String
            label="CustomerLoginId"
            v-model="value.customerLoginId"
            :editMode="editMode"
        />
        <String
            label="CustomerLoginPassword"
            v-model="value.customerLoginPassword"
            :editMode="editMode"
        />
        <String
            label="CustomerRole"
            v-model="value.customerRole"
            :editMode="editMode"
        />
        <String
            label="CustomerName"
            v-model="value.customerName"
            :editMode="editMode"
        />
        <String
            label="CustomerRrn"
            v-model="value.customerRrn"
            :editMode="editMode"
        />
        <Number
            label="CustomerAge"
            v-model="value.customerAge"
            :editMode="editMode"
        />
        <String
            label="CustomerGender"
            v-model="value.customerGender"
            :editMode="editMode"
        />
        <String
            label="CustomerAddress"
            v-model="value.customerAddress"
            :editMode="editMode"
        />
        <String
            label="CustomerEmail"
            v-model="value.customerEmail"
            :editMode="editMode"
        />
        <String
            label="CustomerPhone"
            v-model="value.customerPhone"
            :editMode="editMode"
        />
        <String
            label="CustomerJob"
            v-model="value.customerJob"
            :editMode="editMode"
        />
        <String
            label="CustomerHasChildren"
            v-model="value.customerHasChildren"
            :editMode="editMode"
        />
        <String
            label="CustomerIsMarried"
            v-model="value.customerIsMarried"
            :editMode="editMode"
        />
        <String
            label="CustomerDiseaseList"
            v-model="value.customerDiseaseList"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'Customer',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "customers",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
